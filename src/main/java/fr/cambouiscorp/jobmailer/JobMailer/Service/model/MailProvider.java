package fr.cambouiscorp.jobmailer.JobMailer.Service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MailProvider {

    GMAIL("smtp.gmail.com", 587, "imap.gmail.com", 993),
    IONOS("smtp.ionos.fr", 587, "imap.ionos.fr", 993),
    OUTLOOK("smtp.office365.com", 587, "outlook.office365.com", 993);

    private final String smtpHost;
    private final int smtpPort;
    private final String imapHost;
    private final int imapPort;
}