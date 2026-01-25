package fr.cambouiscorp.jobmailer.JobMailer.Service.client.gmail;

import fr.cambouiscorp.jobmailer.JobMailer.Service.port.MailSessionContext;
import jakarta.mail.Store;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GmailSessionContext implements MailSessionContext {

    private final Store store;

}