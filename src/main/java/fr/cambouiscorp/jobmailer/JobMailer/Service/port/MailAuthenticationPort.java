package fr.cambouiscorp.jobmailer.JobMailer.Service.port;

import fr.cambouiscorp.jobmailer.JobMailer.Service.model.MailAccount;
import org.springframework.stereotype.Service;

public interface MailAuthenticationPort {

    MailSessionContext authenticate(MailAccount account);
}