    package fr.cambouiscorp.jobmailer.JobMailer.mailaccount.infra.adapter.factory;

    import fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.entity.MailAccount;
    import fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.port.JavaMailSenderFactory;
    import org.springframework.mail.javamail.JavaMailSender;
    import org.springframework.mail.javamail.JavaMailSenderImpl;
    import org.springframework.stereotype.Component;

    import java.util.Properties;

    @Component
    public class MailSenderFactory implements JavaMailSenderFactory {

        @Override
        public JavaMailSender apply(MailAccount mailAccount) {
            JavaMailSenderImpl sender = new JavaMailSenderImpl();
            sender.setHost(mailAccount.getMailProvider().getSmtpHost());
            sender.setPort(mailAccount.getMailProvider().getSmtpPort());
            sender.setUsername(mailAccount.getEmail());
            sender.setPassword(mailAccount.getPassword());

            Properties props = sender.getJavaMailProperties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            return  sender;
        }
    }
