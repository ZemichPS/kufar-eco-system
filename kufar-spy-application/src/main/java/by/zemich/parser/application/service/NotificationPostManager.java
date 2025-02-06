package by.zemich.parser.application.service;

import by.zemich.parser.domain.model.Notification;
import by.zemich.parser.domain.service.textpostprocessors.api.PostTextProcessor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.InputStream;

@Service
@AllArgsConstructor
public class NotificationPostManager {

    private final ImageService imageService;

    public SendPhoto create(Notification notification) {
        String imageName = notification.getImageName();
        try {
            InputStream postImageInputStream = imageService.downloadNotificationImage(imageName);
            InputFile photo = new InputFile(postImageInputStream, imageName);
            String text = processPostText(notification.getTitle(), notification.getContent());
            return SendPhoto.builder()
                    .photo(photo)
                    .chatId("54504156056")
                    .parseMode("HTML")
                    .caption(text)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String processPostText(String title, String content) {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(PostTextProcessor.getBoldHtmlStyle(title))
                .append("\n\n")
                .append(content)
                .toString();
    }
}
