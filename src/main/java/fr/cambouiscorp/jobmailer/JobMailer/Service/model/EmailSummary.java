package fr.cambouiscorp.jobmailer.JobMailer.Service.model;

import java.util.Date;

public record EmailSummary(
        int messageId,
        String subject,
        String sender,
        Date sentDate,
        boolean isRead
) {}