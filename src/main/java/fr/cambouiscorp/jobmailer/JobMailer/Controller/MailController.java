package fr.cambouiscorp.jobmailer.JobMailer.Controller;

import fr.cambouiscorp.jobmailer.JobMailer.Service.client.AbstractMailClient;
import fr.cambouiscorp.jobmailer.JobMailer.Service.model.MailAccount;
import fr.cambouiscorp.jobmailer.JobMailer.Service.model.MailProviderType;
import fr.cambouiscorp.jobmailer.JobMailer.Service.port.MailAuthenticationPort;
import fr.cambouiscorp.jobmailer.JobMailer.Service.port.MailSessionContext;
import fr.cambouiscorp.jobmailer.api.MailApi;
import fr.cambouiscorp.jobmailer.model.MailstatusDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class MailController implements MailApi {

    private final MailAuthenticationPort authService;
    private final AbstractMailClient mailClient;

    @Value("${USER_MAIL}")
    private String username;

    @Override
    public MailstatusDTO getStatus() {
        MailAccount account = new MailAccount(
                "1",
                username,
                MailProviderType.GMAIL
        );

        MailSessionContext context = authService.authenticate(account);
        try {
            MailstatusDTO status = new MailstatusDTO();
            status.setStatus("Connected to Gmail as " + account.getEmail());
            return status;
        } catch (Exception e) {
            throw new RuntimeException("Gmail error: " + e.getMessage(), e);
        }
    }
}
