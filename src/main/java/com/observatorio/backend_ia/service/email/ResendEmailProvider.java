package com.observatorio.backend_ia.service.email;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@Component
@ConditionalOnProperty(name = "app.email.provider", havingValue = "resend")
public class ResendEmailProvider implements EmailProvider {

    private final TemplateEngine templateEngine;
    private final Resend resend;
    private final String fromAddress;
    private final String defaultRecipient;

    public ResendEmailProvider(
            TemplateEngine templateEngine,
            @Value("${resend.api.key}") String apiKey,
            @Value("${app.mail.from:cristru8@gmail.com}") String fromAddress,
            @Value("${app.default.recipient:}") String defaultRecipient
    ) {
        this.templateEngine = templateEngine;
        this.resend = new Resend(apiKey);
        this.fromAddress = fromAddress;
        this.defaultRecipient = defaultRecipient;
    }

    @Override
    public void sendIdeaEmail(String to, String name, String idea, String ethicalConcern) throws MessagingException {
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

        try {
            CreateEmailOptions options = CreateEmailOptions.builder()
                    .from(fromAddress)
                    .to(recipient)
                    .subject("Nueva idea enviada por " + name)
                    .html(html)
                    .build();

            var response = resend.emails().send(options);
            log.info("Email enviado via Resend. ID: {}", response.getId());

        } catch (ResendException e) {
            log.error("Error al enviar email via Resend: {}", e.getMessage());
            throw new MessagingException("Fallo al enviar email con Resend: " + e.getMessage());
        }
    }

    @Override
    public String providerName() {
        return "resend";
    }
}