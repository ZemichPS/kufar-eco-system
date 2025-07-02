package by.zemich.telegrambotservice.application.service;

import by.zemich.telegrambotservice.application.service.api.FilesHandler;
import by.zemich.telegrambotservice.application.service.exception.FileHandlerException;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class MinioFilesService implements FilesHandler {

    private final MinioClient minioClient;

    @Override
    public void upload(String fileName, InputStream inputStream, String to) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(to)
                    .object(fileName)
                    .stream(inputStream, inputStream.available(), -1)
                    .build()
            );
        } catch (Exception exception) {
            throw new FileHandlerException(exception.getMessage());
        }
    }

    @Override
    public InputStream download(String fileName, String from) {
        try {
            return minioClient.getObject(GetObjectArgs.builder()
                    .bucket(from)
                    .object(fileName)
                    .build()
            );
        } catch (Exception exception) {
            throw new FileHandlerException(exception.getMessage());
        }
    }
}
