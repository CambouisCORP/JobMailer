package fr.cambouiscorp.jobmailer.JobMailer.Service.Impl;

import fr.cambouiscorp.jobmailer.JobMailer.Service.client.AbstractMailClient;
import fr.cambouiscorp.jobmailer.JobMailer.Service.port.MailAuthenticationPort;
import fr.cambouiscorp.jobmailer.JobMailer.Service.port.MailSessionContext;
import fr.cambouiscorp.jobmailer.JobMailer.Service.port.MailUserActionService;
import fr.cambouiscorp.jobmailer.JobMailer.Service.model.MailAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailUserActionServiceImpl implements MailUserActionService {

    private static final String TEST_RECIPIENT = "recipient@example.com";
    private static final String TEST_SUBJECT = "Test subject";
    private static final String TEST_BODY = "Email body";

    private final MailAuthenticationPort authService;
    private final AbstractMailClient mailClient;

    @Override
    public void followUp(MailAccount account) {
        log.info("Following up on mails for account: {}", account.getEmail());
        try {
            MailSessionContext context = authService.authenticate(account);
            mailClient.readInbox(context);
        } catch (Exception e) {
            log.error("Error during mail follow-up", e);
            throw e;
        }
    }

    @Override
    public void sendInitialMail(MailAccount account) {
        log.info("Sending initial mail for account: {}", account.getEmail());
        try {
            MailSessionContext context = authService.authenticate(account);
            mailClient.send(context, TEST_RECIPIENT, TEST_SUBJECT, TEST_BODY);
            log.info("Initial mail sent successfully");
        } catch (Exception e) {
            log.error("Error sending initial mail", e);
            throw e;
        }
    }
}
