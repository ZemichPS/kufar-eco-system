package by.zemich.telegrambotservice.application.service.botscenarious.adcreation.action;

import by.zemich.telegrambotservice.application.service.TelegramSender;
import by.zemich.telegrambotservice.application.service.botscenarious.adcreation.AdCreationState;
import by.zemich.telegrambotservice.application.service.botscenarious.adcreation.AddAdvertisementEvent;
import by.zemich.telegrambotservice.application.service.openfeign.CategoryOpenFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryInputAction implements Action<AdCreationState, AddAdvertisementEvent> {

    private final TelegramSender<SendMessage> telegramSender;
    private final CategoryOpenFeign categoryOpenFeign;


    @Override
    public void execute(StateContext<AdCreationState, AddAdvertisementEvent> stateContext) {
        Long chatId = stateContext.getStateMachine().getExtendedState().get("chaId", Long.class);
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text("Выберите категорию")
                .replyMarkup(getReplyKeyboardMarkup())
                .build();

        telegramSender.send(sendMessage);
    }

    private ReplyKeyboardMarkup getReplyKeyboardMarkup() {
        List<KeyboardRow> keyboard = categoryOpenFeign.getCategories()
                .stream()
                .map(KeyboardButton::new)
                .map(keyboardButton -> new KeyboardRow(List.of(keyboardButton)))
                .toList();
        return ReplyKeyboardMarkup.builder()
                .keyboard(keyboard)
                .build();
    }

}
