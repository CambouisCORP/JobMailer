package fr.cambouiscorp.jobmailer.JobMailer.Controller;

import fr.cambouiscorp.jobmailer.JobMailer.Service.model.EmailSummary;
import fr.cambouiscorp.jobmailer.JobMailer.Service.model.MailAccount;
import fr.cambouiscorp.jobmailer.JobMailer.Service.model.MailProvider;
import fr.cambouiscorp.jobmailer.JobMailer.Service.port.MailPort;
import fr.cambouiscorp.jobmailer.api.MailApi;
import fr.cambouiscorp.jobmailer.model.MailstatusDTO;
import fr.cambouiscorp.jobmailer.model.PostFullDTO;
import fr.cambouiscorp.jobmailer.model.PostLightDTO;
import fr.cambouiscorp.jobmailer.model.SendMail200Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
public class MailController implements MailApi {

    private final MailPort mailPort;

    @Value("${USER_MAIL}")
    private String username;

    @Value("${USER_PASSWORD}")
    private String password;

    @Override
    public List<PostLightDTO> getInbox() {
        MailAccount account = new MailAccount(
                UUID.randomUUID(),
                username,
                MailProvider.GMAIL,
                password
        );

        List<EmailSummary> emails = mailPort.readInbox(account, 0, 20);
        System.out.println(emails);

        return null;
    }

    @Override
    public MailstatusDTO getStatus() {
        MailstatusDTO mailstatusDTO = new MailstatusDTO();
        mailstatusDTO.setStatus("Mail service is running");
        return mailstatusDTO;
    }

    @Override
    public SendMail200Response sendMail(PostFullDTO postFullDTO) {
        MailAccount account = new MailAccount(
                UUID.randomUUID(),
                username,
                MailProvider.GMAIL,
                password
        );
        mailPort.send(account, postFullDTO.getSender(),  postFullDTO.getSubject(), postFullDTO.getBody());

        return null;
    }

}
