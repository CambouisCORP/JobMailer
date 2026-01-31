package fr.cambouiscorp.jobmailer.JobMailer;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;

@SpringBootApplication(
        exclude = MailSenderAutoConfiguration.class
)
public class JobMailerApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		SpringApplication.run(JobMailerApplication.class, args);
	}

}
