package fr.cambouiscorp.jobmailer.JobMailer.mailaccount.infra.adapter.smtp;

import fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.dto.SendDTO;
import fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.entity.MailAccount;
import fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.port.JavaMailSenderFactory;
import fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.port.MailSenderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmtpMailSenderAdapter implements MailSenderPort {

    private final JavaMailSenderFactory javaMailSenderFactory;

    @Override
    public Void apply(SendDTO sendDTO) {

        JavaMailSender mailSender = javaMailSenderFactory.apply(sendDTO.getMailAccount());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sendDTO.getMailAccount().getEmail());
        message.setTo(sendDTO.getTo());
        message.setSubject(sendDTO.getSubject());
        message.setText(sendDTO.getBody());

        mailSender.send(message);
        //log.debug("Sent mail email from {} to {} with subject '{}'", sendDTO.getMailAccount().getEmail(), sendDTO.getTo(), sendDTO.getSubject());
        return null;
    }
}
