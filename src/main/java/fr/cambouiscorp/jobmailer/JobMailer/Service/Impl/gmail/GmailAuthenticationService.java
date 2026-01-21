package fr.cambouiscorp.jobmailer.JobMailer.Service.Impl.gmail;

import fr.cambouiscorp.jobmailer.JobMailer.Service.client.gmail.GmailSessionContext;
import fr.cambouiscorp.jobmailer.JobMailer.Service.port.MailAuthenticationPort;
import fr.cambouiscorp.jobmailer.JobMailer.Service.port.MailSessionContext;
import fr.cambouiscorp.jobmailer.JobMailer.Service.model.MailAccount;
import jakarta.mail.Session;
import jakarta.mail.Store;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Slf4j
@RequiredArgsConstructor
@Service
public class GmailAuthenticationService implements MailAuthenticationPort {

    private static final String MAIL_STORE_PROTOCOL = "imaps";
    private static final String GMAIL_IMAP_HOST = "imap.gmail.com";
    private static final String GMAIL_IMAP_PORT = "993";
    private static final String SSL_ENABLED = "true";
    private static final String GMAIL_CONNECT_ERROR = "Unable to connect to Gmail";

    @Value("${USER_MAIL}")
    private String username;

    @Value("${USER_PASSWORD}")
    private String appPassword;

    @Override
    public MailSessionContext authenticate(MailAccount account) {
        try {
            disableSSLVerification();
            Properties props = buildGmailProperties();
            Store store = connectToGmail(props);
            log.info("Gmail connection successful for: {}", account.getEmail());
            return new GmailSessionContext(store);
        } catch (MessagingException e) {
            log.error(GMAIL_CONNECT_ERROR, e);
            throw new IllegalStateException(GMAIL_CONNECT_ERROR, e);
        } catch (Exception e) {
            log.error("Unexpected error during authentication", e);
            throw new RuntimeException(e);
        }
    }

    private Properties buildGmailProperties() {
        Properties props = new Properties();
        props.put("mail.store.protocol", MAIL_STORE_PROTOCOL);
        props.put("mail.imaps.host", GMAIL_IMAP_HOST);
        props.put("mail.imaps.port", GMAIL_IMAP_PORT);
        props.put("mail.imaps.ssl.enable", SSL_ENABLED);
        props.put("mail.imaps.auth", SSL_ENABLED);
        return props;
    }

    private Store connectToGmail(Properties props) throws MessagingException {
        Session session = Session.getInstance(props);
        Store store = session.getStore(MAIL_STORE_PROTOCOL);
        store.connect(username, appPassword);
        return store;
    }

    /**
     * ⚠️ SECURITY WARNING: This method disables SSL verification.
     * To be used ONLY in development/test environments.
     * In production, use valid certificates.
     */
    private void disableSSLVerification() throws Exception {
        log.warn("⚠️  SSL verification disabled - DO NOT use in production!");
        
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[]{
                new javax.net.ssl.X509TrustManager() {
                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
                }
        };

        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("TLS");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
    }
}
