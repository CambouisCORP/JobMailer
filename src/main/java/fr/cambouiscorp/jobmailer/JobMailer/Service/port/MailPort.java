package fr.cambouiscorp.jobmailer.JobMailer.Service.port;

import fr.cambouiscorp.jobmailer.JobMailer.Service.model.EmailSummary;
import fr.cambouiscorp.jobmailer.JobMailer.Service.model.MailAccount;

import java.util.List;

public interface MailPort {

    List<EmailSummary> readInbox(MailAccount account, int page, int size);
    void send(MailAccount account,String to, String subject, String body);
}