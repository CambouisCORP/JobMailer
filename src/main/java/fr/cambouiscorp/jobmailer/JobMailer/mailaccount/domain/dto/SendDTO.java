package fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.dto;

import fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.entity.MailAccount;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SendDTO {
    private final MailAccount mailAccount;
    private final String to;
    private final String subject;
    private final String body;
}
