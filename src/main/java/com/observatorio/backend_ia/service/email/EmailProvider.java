package com.observatorio.backend_ia.service.email;

import jakarta.mail.MessagingException;

public interface EmailProvider {
    void sendIdeaEmail(String to, String name, String idea, String ethicalConcern) throws MessagingException;
    String providerName();
}
