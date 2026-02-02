package com.observatorio.backend_ia.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void sendIdeaEmail(String to, String name, String idea, String ethicalConcern) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("idea", idea);
        context.setVariable("ethicalConcern", ethicalConcern);

        String html = templateEngine.process("email/idea-email", context);

        helper.setTo(to);
        helper.setSubject("Nueva idea enviada por " + name);
        helper.setText(html, true);

        mailSender.send(message);
    }
}
