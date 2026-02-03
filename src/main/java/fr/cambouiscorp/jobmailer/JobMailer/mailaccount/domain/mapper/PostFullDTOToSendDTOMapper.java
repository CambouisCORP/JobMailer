package fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.mapper;

import fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.dto.ReadDTO;
import fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.dto.SendDTO;
import fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.entity.MailAccount;
import fr.cambouiscorp.jobmailer.model.PostFullDTO;
import org.apache.commons.lang3.function.TriFunction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.function.BiFunction;

@Mapper(componentModel = "spring")
public interface PostFullDTOToSendDTOMapper extends BiFunction<PostFullDTO, MailAccount, SendDTO> {

    @Override
    @Mapping(source = "postFullDTO.subject", target = "subject")
    @Mapping(source = "postFullDTO.body", target = "body")
    @Mapping(source = "postFullDTO.to", target = "to")
    SendDTO apply(PostFullDTO postFullDTO, MailAccount mailAccount);
}
