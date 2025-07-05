package by.zemich.telegrambotservice.application.service.commands;

import by.zemich.telegrambotservice.application.service.api.TelegramSender;
import by.zemich.telegrambotservice.application.service.scenarious.adcreation.AdCreationState;
import by.zemich.telegrambotservice.application.service.openfeign.AdvertisementServiceOpenFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryInputStateProcessor implements StateProcessor {

    private final TelegramSender<SendMessage> telegramSender;
    private final AdvertisementServiceOpenFeign advertisementServiceOpenFeign;


    @Override
    public String processState() {
        return AdCreationState.CATEGORY_INPUT.name();
    }

    @Override
    public void proceed(String chatId) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text("Выберите категорию")
                .replyMarkup(getReplyKeyboardMarkup())
                .build();

        telegramSender.send(sendMessage);
    }

    private ReplyKeyboardMarkup getReplyKeyboardMarkup() {
        List<KeyboardRow> keyboard = advertisementServiceOpenFeign.getCategories()
                .stream()
                .map(KeyboardButton::new)
                .map(keyboardButton-> new KeyboardRow(List.of(keyboardButton)))
                .toList();

        return ReplyKeyboardMarkup.builder()
                .keyboard(keyboard)
                .build();
    }

}

