package by.zemich.kufar.application.service;

import by.zemich.kufar.application.service.api.NotificationPostManager;
import by.zemich.kufar.application.service.api.PostManager;
import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.model.Notification;
import by.zemich.kufar.domain.service.textpostprocessors.api.PostTextProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramPostManager implements PostManager<SendPhoto, Advertisement> {

    private final List<PostTextProcessor> postTextProcessors;
    private final PostLimitedCache<UUID, SendPhoto> postLimitedCache = new PostLimitedCache<>(500);
    private final FileLoader fileLoader;

    public SendPhoto create(Advertisement advertisement) {
        return postLimitedCache.computeIfAbsent(advertisement.getId(), id -> createIfNotExists(advertisement));
    }

    private SendPhoto createIfNotExists(Advertisement advertisement) {
        InputFile photo = advertisement.getPhotoLink()
                .map(InputFile::new)
                .orElseGet(() -> {
                    InputStream inputStream = fileLoader.loadResourcesFileAsInputStream("images/default.jpg");
                    return new InputFile(inputStream, UUID.randomUUID() + "jpg");
                });
        String text = processPostText(advertisement);
        return SendPhoto.builder()
                .photo(photo)
                .chatId("54504156056")
                .parseMode("HTML")
                .caption(text)
                .build();
    }


    private String processPostText(Advertisement advertisement) {
        return postTextProcessors.stream()
                .filter(postTextProcessor -> postTextProcessor.isApplicable(advertisement))
                .map(processor -> processor.process(advertisement))
                .filter(s -> !s.isBlank())
                .collect(Collectors.joining("\n"));
    }
}
