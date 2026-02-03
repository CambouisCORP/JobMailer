package fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.port;

import fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.dto.EmailSummary;
import fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.dto.ReadDTO;
import fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.entity.MailAccount;

import java.util.List;
import java.util.function.Function;

public interface MailReaderPort extends Function<ReadDTO, List<EmailSummary>> {
}
