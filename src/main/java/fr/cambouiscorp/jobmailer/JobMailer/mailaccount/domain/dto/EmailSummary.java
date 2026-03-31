package fr.cambouiscorp.jobmailer.JobMailer.mailaccount.domain.dto;

import java.util.Date;

public record EmailSummary(
        int messageId,
        String subject,
        String sender,
        Date sentDate,
        boolean isRead
) {}