package com.observatorio.backend_ia.service.email;

import com.observatorio.backend_ia.model.enums.IdeaCategory;
import jakarta.mail.MessagingException;

public interface EmailProvider {
    void sendIdeaEmail(String to, String name, String idea, IdeaCategory category, String ethicalConcern) throws MessagingException;
    String providerName();
}
