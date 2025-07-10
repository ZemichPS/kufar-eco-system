package by.zemich.telegrambotservice.application.service.scenarious.adcreation.action.render;

import by.zemich.telegrambotservice.application.service.api.TelegramSender;
import by.zemich.telegrambotservice.application.service.scenarious.adcreation.AdCreationState;
import by.zemich.telegrambotservice.application.service.scenarious.adcreation.AddAdvertisementEvent;
import by.zemich.telegrambotservice.application.service.scenarious.api.StateMachineContextHelper;
import by.zemich.telegrambotservice.domain.dto.AdvertisementDraftDto;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Arrays;
import java.util.List;

@Component
public class ConfirmRenderAction extends AdCreationRenderAction {

    public ConfirmRenderAction(TelegramSender<SendMessage> telegramSender) {
        super(
                "Поздравляю! объявление успешно создано. Через некоторое время оно будет опубликовано в канале",
                telegramSender
        );
    }

    @Override
    public void execute(StateContext<AdCreationState, AddAdvertisementEvent> context) {
        StateMachine<AdCreationState, AddAdvertisementEvent> sm = getStateMachine(context);
        Long chatId = StateMachineContextHelper.getChatId(sm);
        fillInAd(sm);
        SendMessage message = createMessage(chatId, ACTION_TEXT, null);
        telegramSender.send(message);
    }

    protected void fillInAd(StateMachine<AdCreationState, AddAdvertisementEvent> stateMachine) {
        String previousStageText = StateMachineContextHelper.getPreviousStageText(stateMachine);
        List<String> attributes = Arrays.stream(previousStageText.split("\\n")).toList();
        AdvertisementDraftDto adDraft = StateMachineContextHelper.getAdDraft(stateMachine);
        adDraft.setAttributes(attributes);
        StateMachineContextHelper.setAdDraft(stateMachine, adDraft);
    }

    @Override
    public AdCreationState getHandleState() {
        return AdCreationState.CONFIRMATION;
    }
}
