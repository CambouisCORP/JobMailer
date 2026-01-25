package fr.cambouiscorp.jobmailer.JobMailer.Service.client;

import fr.cambouiscorp.jobmailer.JobMailer.Service.port.MailSessionContext;

public abstract class AbstractMailClient {

    public abstract void readInbox(MailSessionContext context);

    public abstract void send(
            MailSessionContext context,
            String to,
            String subject,
            String body
    );
}