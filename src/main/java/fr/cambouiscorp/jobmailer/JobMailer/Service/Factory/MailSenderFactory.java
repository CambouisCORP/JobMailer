package fr.cambouiscorp.jobmailer.JobMailer.Service.Factory;

import fr.cambouiscorp.jobmailer.JobMailer.Service.model.MailAccount;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class MailSenderFactory {

    public JavaMailSender create(MailAccount account) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();

        sender.setHost(account.getMailProvider().getSmtpHost());
        sender.setPort(account.getMailProvider().getSmtpPort());
        sender.setUsername(account.getEmail());
        sender.setPassword(account.getPassword());

        Properties props = sender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return sender;
    }
}
