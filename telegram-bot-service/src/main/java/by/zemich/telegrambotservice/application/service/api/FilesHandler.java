package by.zemich.telegrambotservice.application.service.api;

import java.io.FileInputStream;
import java.io.InputStream;

public interface FilesHandler {
    void upload(String fileName, InputStream fileInputStream, String to);

    InputStream download(String fileName, String from);
}
