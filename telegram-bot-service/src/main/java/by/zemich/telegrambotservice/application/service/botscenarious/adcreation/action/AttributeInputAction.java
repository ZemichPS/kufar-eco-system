package by.zemich.telegrambotservice.application.service.botscenarious.adcreation.action;

import by.zemich.telegrambotservice.application.service.api.TelegramSender;
import by.zemich.telegrambotservice.application.service.botscenarious.adcreation.AdCreationState;
import by.zemich.telegrambotservice.application.service.botscenarious.adcreation.AddAdvertisementEvent;
import by.zemich.telegrambotservice.application.service.botscenarious.api.StateMachineContextHelper;
import by.zemich.telegrambotservice.application.service.botscenarious.api.TelegramFileLoader;
import by.zemich.telegrambotservice.domain.dto.AdvertisementDraftDto;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.IOException;

@Component
public class AttributeInputAction extends BaseAdCreationAction {

    private final TelegramFileLoader telegramFileLoader;

    public AttributeInputAction(TelegramSender<SendMessage> telegramSender, TelegramFileLoader telegramFileLoader) {
        super("""
                📝 Отправьте характеристики товара по одному на строку ⏎
                Например:
                чёрный
                б/у
                зарядка
                """, telegramSender);
        this.telegramFileLoader = telegramFileLoader;
    }

    @Override
    public void execute(StateContext<AdCreationState, AddAdvertisementEvent> context) {
        StateMachine<?, ?> sm = context.getStateMachine();
        fillInAd(sm);
        Long chatId = StateMachineContextHelper.getChatId(sm);
        SendMessage message = createMessage(chatId, ACTION_TEXT, null);
        telegramSender.send(message);
    }

    @Override
    protected void fillInAd(StateMachine<?, ?> stateMachine) {
        String fileId = stateMachine.getExtendedState().get("fileId", String.class);
        String filename = null;

        try {
            filename = telegramFileLoader.downloadAndSaveFileToMinio(fileId, "photos");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AdvertisementDraftDto adDraft = StateMachineContextHelper.getAdDraft(stateMachine);
        adDraft.setPhotoName(filename);
        StateMachineContextHelper.setAdDraft(stateMachine, adDraft);
    }
}
