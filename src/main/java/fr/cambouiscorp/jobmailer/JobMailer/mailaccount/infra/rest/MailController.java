package fr.cambouiscorp.jobmailer.JobMailer.mailaccount.infra.rest;

import fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.dto.EmailSummary;
import fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.entity.MailAccount;
import fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.mapper.AccountToReadDTOMapper;
import fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.mapper.PostFullDTOToSendDTOMapper;
import fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.port.MailReaderPort;
import fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.port.MailSenderPort;
import fr.cambouiscorp.jobmailer.JobMailer.provider.entity.MailProvider;
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

    private final MailReaderPort mailReaderPort;
    private final MailSenderPort mailSenderPort;
    private final AccountToReadDTOMapper accountToReadDTOMapper;
    private final PostFullDTOToSendDTOMapper postFullDTOToSendDTOMapper;


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
         List<EmailSummary> emials = mailReaderPort.apply(accountToReadDTOMapper.apply(account,0,20));
        System.out.println("Fetched " + emials.size() + " emails.");
        return null;
    }

    @Override
    public MailstatusDTO getStatus() {
        return null;
    }

    @Override
    public SendMail200Response sendMail(PostFullDTO postFullDTO) {
        MailAccount account = new MailAccount(
                UUID.randomUUID(),
                username,
                MailProvider.GMAIL,
                password
        );
        mailSenderPort.apply(postFullDTOToSendDTOMapper.apply(postFullDTO, account));
        return null;
    }
}
