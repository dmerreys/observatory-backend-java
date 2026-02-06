package com.observatorio.backend_ia.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final TemplateEngine templateEngine;

    @Value("${sendgrid.api.key:}")
    private String sendgridApiKey;

    // destinatario por defecto (app.default.recipient)
    @Value("${app.default.recipient:}")
    private String defaultRecipient;

    // remitente configurable
    @Value("${app.mail.from:cristru8@gmail.com}")
    private String mailFrom;

    private SendGrid sendGridClient;

    @PostConstruct
    private void init() {
        if (sendgridApiKey != null && !sendgridApiKey.isEmpty()) {
            sendGridClient = new SendGrid(sendgridApiKey);
        }
    }

    public void sendIdeaEmail(String to, String name, String idea, String ethicalConcern) throws MessagingException {
        if (sendGridClient == null) {
            throw new IllegalStateException("SendGrid API key not configured");
        }

        // usa destinatario por defecto si no se proporciona
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
            // con la librería oficial se debe usar "mail/send" como endpoint (sin prefijo /v3)
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
}
