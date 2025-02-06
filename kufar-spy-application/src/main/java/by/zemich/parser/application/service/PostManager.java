package by.zemich.parser.application.service;

import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.domain.service.textpostprocessors.api.PostTextProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@CacheConfig(
        cacheManager = "caffeineCacheManagerForPostManager",
        cacheNames = "telegramposts"
)
public class PostManager {

    private final List<PostTextProcessor> postTextProcessors;
    private final FileLoader fileLoader;

    @Cacheable(
            key = "#advertisement.id",
            sync = true
    )
    public SendPhoto create(Advertisement advertisement) {
        return createIfNotExists(advertisement);
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
