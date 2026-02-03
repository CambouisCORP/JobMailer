package fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.port;

import fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.dto.SendDTO;
import fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.entity.MailAccount;

import java.util.function.Function;

public interface MailSenderPort extends Function<SendDTO, Void> {
}
