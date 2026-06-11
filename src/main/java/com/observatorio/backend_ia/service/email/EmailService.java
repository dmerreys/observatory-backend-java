package com.observatorio.backend_ia.service.email;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailProvider emailProvider;

    public void sendIdeaEmail(String to, String name, String idea, String ethicalConcern) throws MessagingException {
        log.info("Enviando email via proveedor: {}", emailProvider.providerName());
        emailProvider.sendIdeaEmail(to, name, idea, ethicalConcern);
    }
}
