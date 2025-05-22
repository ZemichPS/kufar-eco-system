package com.zemich.email_service.application.service;

import com.zemich.email_service.domain.command.SendActivationCodeCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceFacade {
    private final EmailService emailService;
    private final HtmlBodyGenerator htmlBodyGenerator;

    public void sendActivationCode(SendActivationCodeCommand command) {
        String body = htmlBodyGenerator.generateEmailApproveMessageBody(command.getCode());
        String subject = "Код подтверждения регистрации на PartsFlow";
        emailService.sendEmail(command.getTo(), body, subject);
    }

}
