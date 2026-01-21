package fr.cambouiscorp.jobmailer.JobMailer.Service.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class MailAccount {
    private final UUID id;
    private final String email;
    private final MailProviderType providerType;

}
