package fr.cambouiscorp.jobmailer.JobMailer.Service.Impl;

import fr.cambouiscorp.jobmailer.JobMailer.Service.Adapter.AbstractMailAdapter;
import fr.cambouiscorp.jobmailer.JobMailer.Service.Factory.MailSenderFactory;
import org.springframework.stereotype.Service;

@Service
public class SmtpMailAdapter extends AbstractMailAdapter {

    public SmtpMailAdapter(MailSenderFactory mailSenderFactory) {
        super(mailSenderFactory);
    }
}
