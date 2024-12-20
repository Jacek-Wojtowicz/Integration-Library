package com.jw.mail.smtp;

import com.jw.mail.core.exception.MailException;
import com.jw.mail.core.providers.AttachmentEmailProvider;
import com.jw.mail.core.providers.HtmlEmailProvider;
import com.jw.mail.core.providers.SingleEmailProvider;
import com.jw.mail.core.type.ContentType;
import com.jw.mail.core.type.ProviderType;
import com.jw.mail.smtp.entity.SmtpAttachment;
import com.jw.mail.smtp.configuration.SmtpConfiguration;
import jakarta.activation.DataHandler;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;

import static com.jw.mail.smtp.configuration.SmtpConstants.*;
import static jakarta.mail.Message.RecipientType.TO;


@Component
@RequiredArgsConstructor
public class SmtpEmailProvider
        implements SingleEmailProvider, HtmlEmailProvider, AttachmentEmailProvider<SmtpAttachment> {

    private final SmtpConfiguration configuration;

    public static Builder builder() {
        return new Builder();
    }

    private Session createSession() {
        Properties props = new Properties();
        props.put(AUTH, configuration.isAuth());
        props.put(DEFAULT_ENABLE_TLS, configuration.isTls());
        props.put(HOST, configuration.getHost());
        props.put(PORT, configuration.getPort());

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(configuration.getUsername(), configuration.getPassword());
            }
        });
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = getMimeMessage(to, subject);
            message.setText(body);

            Transport.send(message);
        } catch (Exception e) {
            throw new MailException(ProviderType.SMTP, "Failed to send single mail via SMTP", e);
        }
    }

    @Override
    public void sendHtmlEmail(String to, String subject, String htmlContent) {
        try {
            Message message = getMimeMessage(to, subject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(htmlContent, ContentType.HTML);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        } catch (Exception e) {
            throw new MailException(ProviderType.SMTP, "Failed to send HTML mail via SMTP", e);
        }
    }

    private MimeMessage getMimeMessage(String to, String subject) throws MessagingException {
        MimeMessage message = new MimeMessage(createSession());
        message.setFrom(new InternetAddress(configuration.getFrom()));
        message.setRecipients(TO, InternetAddress.parse(to));
        message.setSubject(subject);
        return message;
    }

    @Override
    public void sendEmailWithAttachments(String to, String subject, String body, List<SmtpAttachment> mailAttachments) {
        try {
            Message message = getMimeMessage(to, subject);
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(body);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);

            for (SmtpAttachment attachment : mailAttachments) {
                MimeBodyPart attachmentPart = new MimeBodyPart();
                DataHandler attachmentFolder = attachment.toMailProviderAttachmentFormat();
                String fileName = attachment.getFileName();
                attachmentPart.setDataHandler(attachmentFolder);
                attachmentPart.setFileName(fileName);
                multipart.addBodyPart(attachmentPart);
            }

            message.setContent(multipart);
            Transport.send(message);
        } catch (Exception e) {
            throw new MailException(ProviderType.SMTP, "Failed to send attachment mail via SMTP", e);
        }
    }

    public static class Builder {

        private final SmtpConfiguration configuration = new SmtpConfiguration();

        public Builder host(String host) {
            configuration.setHost(host);
            return this;
        }

        public Builder port(int port) {
            configuration.setPort(port);
            return this;
        }

        public Builder username(String username) {
            configuration.setUsername(username);
            return this;
        }

        public Builder password(String password) {
            configuration.setPassword(password);
            return this;
        }

        public Builder from(String from) {
            configuration.setFrom(from);
            return this;
        }

        public SmtpEmailProvider build() {
            return new SmtpEmailProvider(configuration);
        }

    }

}
