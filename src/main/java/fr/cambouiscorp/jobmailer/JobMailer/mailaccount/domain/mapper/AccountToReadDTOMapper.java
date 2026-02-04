package fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.mapper;

import fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.dto.ReadDTO;

import fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.entity.MailAccount;
import org.apache.commons.lang3.function.TriFunction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountToReadDTOMapper extends TriFunction<MailAccount, Integer, Integer, ReadDTO> {

    @Override
    ReadDTO apply(MailAccount mailAccount, Integer page, Integer size);
}
