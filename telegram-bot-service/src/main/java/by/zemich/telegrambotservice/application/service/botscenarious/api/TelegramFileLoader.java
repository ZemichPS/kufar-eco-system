package by.zemich.telegrambotservice.application.service.botscenarious.api;

import by.zemich.telegrambotservice.application.service.MinioFilesService;
import by.zemich.telegrambotservice.application.service.api.TelegramFileDownloader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.File;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class TelegramFileLoader {

    private final MinioFilesService minioFilesService;
    private final TelegramFileDownloader telegramFileDownloader;

    public String downloadAndSaveFileToMinio(String fileId, String bucketName) throws IOException {
        GetFile getFile = new GetFile(fileId);
        File telegramFile = telegramFileDownloader.downloadFile(getFile);
        String filePath = telegramFile.getFilePath();
        String downloadUrl = "https://api.telegram.org/file/bot" + telegramFileDownloader.getToken() + "/" + filePath;
        InputStream inputStream = new URL(downloadUrl).openStream();
        String objectName = UUID.randomUUID() + "_" + Paths.get(filePath).getFileName().toString();
        minioFilesService.upload(objectName, inputStream, bucketName);
        inputStream.close();
        return objectName;
    }
}
