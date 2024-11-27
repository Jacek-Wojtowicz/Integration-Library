package com.jw.mail.sendgrid;

import com.jw.core.variable.VariablePairs;
import com.jw.mail.core.exception.MailException;
import com.jw.mail.core.providers.SingleEmailProvider;
import com.jw.mail.core.providers.TemplateEmailProvider;
import com.jw.mail.core.type.ProviderType;
import com.jw.mail.sendgrid.configuration.SendgridConfiguration;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.jw.mail.core.type.ContentType.TEXT;
import static com.jw.mail.sendgrid.configuration.SendgridConstants.ENDPOINT;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendgridSingleEmailProvider
        implements SingleEmailProvider, TemplateEmailProvider {

    private final SendgridConfiguration configuration;

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        String sender = configuration.getSender();
        Email from = new Email(sender);
        Email receiver = new Email(to);
        Content content = new Content(TEXT, body);
        Mail mail = new Mail(from, subject, receiver, content);

        String apiKey = configuration.getApiKey();
        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint(ENDPOINT);

        try {
            String mailBody = mail.build();
            request.setBody(mailBody);
            Response response = sg.api(request);
            log.info(response.getBody());
        } catch (Exception e) {
            throw new MailException(ProviderType.SENDGRID, "Failed to send mail via SendGrid", e);
        }
    }

    @Override
    public void sendTemplateEmail(String to, String subject, String templateName, VariablePairs variables) {
        String sender = configuration.getSender();
        Email from = new Email(sender);

        Email receiver = new Email(to);
        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setSubject(subject);
        mail.setTemplateId(templateName);

        Personalization personalization = new Personalization();
        personalization.addTo(receiver);

        if (variables != null && variables.isNotEmpty()) {
            for (var entry : variables.getEntrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                personalization.addDynamicTemplateData(key, value);
            }
        }

        mail.addPersonalization(personalization);

        String apiKey = configuration.getApiKey();
        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint(ENDPOINT);

        try {
            String mailBody = mail.build();
            request.setBody(mailBody);
            Response response = sg.api(request);
            log.info(response.getBody());
        } catch (Exception e) {
            throw new MailException(ProviderType.SENDGRID, "Failed to send HTML mail via SendGrid", e);
        }
    }

    public static class Builder {

        private final SendgridConfiguration configuration = new SendgridConfiguration();

        public Builder apiKey(String apiKey) {
            configuration.setApiKey(apiKey);
            return this;
        }

        public Builder sender(String sender) {
            configuration.setSender(sender);
            return this;
        }

        public SendgridSingleEmailProvider build() {
            return new SendgridSingleEmailProvider(configuration);
        }

    }

}
