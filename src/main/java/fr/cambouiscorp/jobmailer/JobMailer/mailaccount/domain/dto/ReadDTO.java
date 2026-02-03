package fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.dto;

import fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.entity.MailAccount;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReadDTO {
    private final MailAccount mailAccount;
    private final int page;
    private final int size;
}
