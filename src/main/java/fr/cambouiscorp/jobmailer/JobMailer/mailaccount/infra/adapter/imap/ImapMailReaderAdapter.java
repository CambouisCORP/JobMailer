package fr.cambouiscorp.jobmailer.JobMailer.mailaccount.infra.adapter.imap;

import fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.dto.EmailSummary;
import fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.dto.ReadDTO;
import fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.port.MailReaderPort;
import jakarta.mail.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Service
public class ImapMailReaderAdapter implements MailReaderPort {

    @Override
    public List<EmailSummary> apply(ReadDTO dto) {
        Store store = null;
        Folder inbox = null;
        List<EmailSummary> summaries = new ArrayList<>();

        try {
            Properties props = new Properties();
            props.put("mail.store.protocol", "imaps");
            props.put("mail.imaps.host", dto.getMailAccount().getMailProvider().getImapHost());
            props.put("mail.imaps.port", String.valueOf(dto.getMailAccount().getMailProvider().getImapPort()));
            props.put("mail.imaps.ssl.enable", "true");

            Session session = Session.getInstance(props);
            store = session.getStore("imaps");
            store.connect(dto.getMailAccount().getMailProvider().getImapHost(), dto.getMailAccount().getEmail(), dto.getMailAccount().getPassword());

            inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            int totalMessages = inbox.getMessageCount();
            if (totalMessages == 0) return Collections.emptyList();

            int end = totalMessages - (dto.getPage() * dto.getSize());
            int start = end - dto.getSize() + 1;

            if (end < 1) return Collections.emptyList();
            if (start < 1) start = 1;

            Message[] messages = inbox.getMessages(start, end);

            FetchProfile fp = new FetchProfile();
            fp.add(FetchProfile.Item.ENVELOPE);
            fp.add(FetchProfile.Item.FLAGS);
            inbox.fetch(messages, fp);

            for (Message msg : messages) {
                summaries.add(new EmailSummary(
                        msg.getMessageNumber(),
                        msg.getSubject(),
                        msg.getFrom()[0].toString(),
                        msg.getSentDate(),
                        msg.isSet(Flags.Flag.SEEN)
                ));
            }
            Collections.reverse(summaries);
            return summaries;

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to read inbox", e);
        } finally {
            try {
                if (inbox != null && inbox.isOpen()) inbox.close(false);
                if (store != null && store.isConnected()) store.close();
            } catch (MessagingException e) {
                //log.error("Error closing mail resources", e);
            }
        }
    }
}
