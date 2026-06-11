package com.observatorio.backend_ia.service.email;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.objects.Personalization;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;;

@Slf4j
@Component
@ConditionalOnProperty(name = "app.email.provider", havingValue = "sendgrid")
public class SendGridEmailProvider implements EmailProvider {

    private final TemplateEngine templateEngine;

    @Value("${sendgrid.api.key:}")
    private String sendgridApiKey;

    @Value("${app.default.recipient:}")
    private String defaultRecipient;

    @Value("${app.mail.from:cristru8@gmail.com}")
    private String mailFrom;

    private SendGrid sendGridClient;

    public SendGridEmailProvider(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @PostConstruct
    private void init() {
        if (sendgridApiKey != null && !sendgridApiKey.isEmpty()) {
            sendGridClient = new SendGrid(sendgridApiKey);
        }
    }

    @Override
    public void sendIdeaEmail(String to, String name, String idea, String ethicalConcern) throws MessagingException {
        if (sendGridClient == null) {
            throw new IllegalStateException("SendGrid API key not configured");
        }

        String recipient = to;
        if (recipient == null || recipient.trim().isEmpty()) {
            if (defaultRecipient == null || defaultRecipient.trim().isEmpty()) {
                throw new IllegalArgumentException("No recipient provided and app.default.recipient is not configured");
            }
            recipient = defaultRecipient;
        }

        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("idea", idea);
        context.setVariable("ethicalConcern", ethicalConcern);

        String html = templateEngine.process("email/idea-email", context);

        Email from = new Email(mailFrom);
        Email toEmail = new Email(recipient);
        Content content = new Content("text/html", html);

        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setSubject("Nueva idea enviada por " + name);
        mail.addContent(content);

        Personalization personalization = new Personalization();
        personalization.addTo(toEmail);
        mail.addPersonalization(personalization);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            com.sendgrid.Response response = sendGridClient.api(request);
            int status = response.getStatusCode();
            if (status < 200 || status >= 300) {
                throw new MessagingException("SendGrid failed with status: " + status + " body: " + response.getBody());
            }
        } catch (MessagingException mex) {
            throw mex;
        } catch (Exception ex) {
            throw new MessagingException("Failed to send email via SendGrid: " + ex.getMessage());
        }
    }

    @Override
    public String providerName() {
        return "sendgrid";
    }
}
