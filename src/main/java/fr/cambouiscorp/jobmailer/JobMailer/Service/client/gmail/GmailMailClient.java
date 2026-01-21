package fr.cambouiscorp.jobmailer.JobMailer.Service.client.gmail;

import fr.cambouiscorp.jobmailer.JobMailer.Service.client.AbstractMailClient;
import fr.cambouiscorp.jobmailer.JobMailer.Service.port.MailSessionContext;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class GmailMailClient extends AbstractMailClient {

    private static final String INVALID_CONTEXT_MESSAGE = "Invalid context for Gmail";
    private static final String INBOX_READ_ERROR = "Gmail inbox read error";
    private static final String INBOX_FOLDER = "INBOX";

    @Override
    public void readInbox(MailSessionContext context) {
        GmailSessionContext gmailContext = validateContext(context);
        try (Folder inbox = gmailContext.getStore().getFolder(INBOX_FOLDER)) {
            inbox.open(Folder.READ_ONLY);
            Message[] messages = inbox.getMessages();
            log.info("Reading {} messages from INBOX", messages.length);
            
            for (Message msg : messages) {
                logMessage((MimeMessage) msg);
            }
        } catch (MessagingException e) {
            log.error(INBOX_READ_ERROR, e);
            throw new IllegalStateException(INBOX_READ_ERROR, e);
        }
    }

    private void logMessage(MimeMessage msg) {
        try {
            String from = msg.getFrom() != null && msg.getFrom().length > 0 
                ? msg.getFrom()[0].toString() 
                : "Unknown";
            log.debug("From: {} | Subject: {}", from, msg.getSubject());
        } catch (MessagingException e) {
            log.warn("Unable to read message details", e);
        }
    }

    @Override
    public void send(MailSessionContext context, String to, String subject, String body) {
        validateContext(context);
        log.info("[PLACEHOLDER] Sending mail to: {} with subject: {}", to, subject);
        // TODO: Implement actual mail sending
    }

    public List<String> listFolders(MailSessionContext context) throws MessagingException {
        GmailSessionContext gmailContext = validateContext(context);
        List<String> names = new ArrayList<>();
        Folder[] folders = gmailContext.getStore().getDefaultFolder().list();
        
        for (Folder folder : folders) {
            names.add(folder.getFullName());
        }
        log.debug("Folders found: {}", names.size());
        return names;
    }

    public int getInboxCount(MailSessionContext context) throws MessagingException {
        GmailSessionContext gmailContext = validateContext(context);
        try (Folder inbox = gmailContext.getStore().getFolder(INBOX_FOLDER)) {
            inbox.open(Folder.READ_ONLY);
            int count = inbox.getMessageCount();
            log.debug("Number of messages in INBOX: {}", count);
            return count;
        }
    }

    private GmailSessionContext validateContext(MailSessionContext context) {
        if (!(context instanceof GmailSessionContext gmailContext)) {
            throw new IllegalArgumentException(INVALID_CONTEXT_MESSAGE);
        }
        return gmailContext;
    }
}
