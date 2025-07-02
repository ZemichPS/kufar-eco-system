package by.zemich.telegrambotservice.application.service.api;

import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.File;

public interface TelegramFileDownloader {

    File downloadFile(GetFile getFile);

    String getToken();


}
