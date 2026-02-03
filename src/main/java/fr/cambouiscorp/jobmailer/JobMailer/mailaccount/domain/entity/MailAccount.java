package fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.entity;

import fr.cambouiscorp.jobmailer.JobMailer.provider.entity.MailProvider;
import lombok.*;

import java.util.UUID;


@Getter
@Setter
@RequiredArgsConstructor
//@Entity
@AllArgsConstructor
public class MailAccount {
    //@Id
    private UUID id;

    private String email;

    private MailProvider mailProvider;

    private String password;

}
