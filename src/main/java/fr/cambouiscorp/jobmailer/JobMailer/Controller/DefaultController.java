package fr.cambouiscorp.jobmailer.JobMailer.Controller;

import fr.cambouiscorp.jobmailer.api.DefaultApi;
import fr.cambouiscorp.jobmailer.model.HealthcheckDTO;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController implements DefaultApi {

    @Override
    public HealthcheckDTO get() {
        HealthcheckDTO healthcheckDTO = new HealthcheckDTO();
        healthcheckDTO.setStatus("OK");
        return healthcheckDTO;
    }
}
