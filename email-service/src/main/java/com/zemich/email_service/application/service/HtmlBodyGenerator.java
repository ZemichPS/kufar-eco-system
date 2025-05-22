package com.zemich.email_service.application.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Component
public class HtmlBodyGenerator {

    private final String CONFIRM_CODE_TEMPLATE = "/templates/confirmation_body.htm";

    public String generateEmailApproveMessageBody(String code) {
        String emailBody = "";
        try {
            emailBody = loadTemplate(CONFIRM_CODE_TEMPLATE);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить шаблон", e);
        }
        emailBody = emailBody.replace("[[code]]", code);
        return emailBody;
    }

    private String loadTemplate(String templateFile) throws IOException {
        ClassPathResource resource = new ClassPathResource(templateFile);
        return Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);
    }
}
