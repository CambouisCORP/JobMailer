package fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.port;

import fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.entity.MailAccount;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.function.Function;

public interface JavaMailSenderFactory extends Function<MailAccount, JavaMailSender> {
}
