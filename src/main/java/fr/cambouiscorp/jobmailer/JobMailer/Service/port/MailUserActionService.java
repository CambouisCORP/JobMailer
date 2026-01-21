package fr.cambouiscorp.jobmailer.JobMailer.Service.port;

import fr.cambouiscorp.jobmailer.JobMailer.Service.model.MailAccount;

public interface MailUserActionService {

    void followUp(MailAccount account);

    void sendInitialMail(MailAccount account);
}