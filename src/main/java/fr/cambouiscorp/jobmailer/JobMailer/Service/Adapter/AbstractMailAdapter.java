package fr.cambouiscorp.jobmailer.JobMailer.Service.Adapter;

import fr.cambouiscorp.jobmailer.JobMailer.Service.Factory.MailSenderFactory;
import fr.cambouiscorp.jobmailer.JobMailer.Service.model.EmailSummary;
import fr.cambouiscorp.jobmailer.JobMailer.Service.model.MailAccount;
import fr.cambouiscorp.jobmailer.JobMailer.Service.port.MailPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import jakarta.mail.*;

import java.util.*;


import java.util.Properties;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractMailAdapter implements MailPort {

    protected final MailSenderFactory mailSenderFactory;

    @Override
    public void send(MailAccount account, String to, String subject, String body) {

        JavaMailSender mailSender = mailSenderFactory.create(account);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(account.getEmail());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
        log.debug("Sent mail email from {} to {} with subject '{}'", account.getEmail(), to, subject);
    }



    @Override
    public List<EmailSummary> readInbox(MailAccount settings, int page, int size) {
        Store store = null;
        Folder inbox = null;
        List<EmailSummary> summaries = new ArrayList<>();

        try {
            Properties props = new Properties();
            props.put("mail.store.protocol", "imaps");
            props.put("mail.imaps.host", settings.getMailProvider().getImapHost());
            props.put("mail.imaps.port", String.valueOf(settings.getMailProvider().getImapPort()));
            props.put("mail.imaps.ssl.enable", "true");

            Session session = Session.getInstance(props);
            store = session.getStore("imaps");
            store.connect(settings.getMailProvider().getImapHost(), settings.getEmail(), settings.getPassword());

            inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            int totalMessages = inbox.getMessageCount();
            if (totalMessages == 0) return Collections.emptyList();

            int end = totalMessages - (page * size);
            int start = end - size + 1;

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
                log.error("Error closing mail resources", e);
            }
        }
    }

}