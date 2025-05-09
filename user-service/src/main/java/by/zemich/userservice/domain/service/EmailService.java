package by.zemich.userservice.domain.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendEmail(String to, String htmlBody, String subject) {
        MimeMessage message;
        try {
            message = createHtmlEmail(to, htmlBody, subject);
        } catch (MessagingException e) {
            // TODO создать и бросить кастомное бизнес исключение
            throw new RuntimeException("Не удалось создать email");
        }
        mailSender.send(message);
    }

    private MimeMessage createHtmlEmail(String to, String htmlBody, String subject) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        helper.setFrom(sender);
        return message;
    }


}
