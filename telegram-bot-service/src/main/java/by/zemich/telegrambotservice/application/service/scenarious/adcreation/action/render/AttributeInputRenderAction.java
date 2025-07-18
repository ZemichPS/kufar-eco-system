package by.zemich.telegrambotservice.application.service.scenarious.adcreation.action.render;

import by.zemich.telegrambotservice.application.service.api.TelegramSender;
import by.zemich.telegrambotservice.application.service.scenarious.adcreation.AdCreationState;
import by.zemich.telegrambotservice.application.service.scenarious.adcreation.AddAdvertisementEvent;
import by.zemich.telegrambotservice.application.service.scenarious.api.StateMachineContextHelper;
import by.zemich.telegrambotservice.application.service.scenarious.api.TelegramFileLoader;
import by.zemich.telegrambotservice.domain.dto.AdvertisementDraftDto;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.IOException;

@Component
public class AttributeInputRenderAction extends AdCreationRenderAction {

    private final TelegramFileLoader telegramFileLoader;

    public AttributeInputRenderAction(TelegramSender<SendMessage> telegramSender, TelegramFileLoader telegramFileLoader) {
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
        StateMachine<AdCreationState, AddAdvertisementEvent> sm = context.getStateMachine();
        Long chatId = StateMachineContextHelper.getChatId(context);
        saveTriggerEvent(context);
        SendMessage message = createMessage(chatId, ACTION_TEXT, null);
        telegramSender.send(message);
    }


    protected void fillInAd(StateMachine<AdCreationState, AddAdvertisementEvent> stateMachine) {
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

    @Override
    public AdCreationState getHandleState() {
        return AdCreationState.ATTRIBUTES_INPUT;
    }
}
