package by.zemich.telegrambotservice.application.service.botscenarious.adcreation.action;

import by.zemich.telegrambotservice.application.service.api.TelegramSender;
import by.zemich.telegrambotservice.application.service.botscenarious.adcreation.AdCreationState;
import by.zemich.telegrambotservice.application.service.botscenarious.adcreation.AddAdvertisementEvent;
import by.zemich.telegrambotservice.application.service.botscenarious.api.StateMachineContextHelper;
import by.zemich.telegrambotservice.domain.dto.AdvertisementDraftDto;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Arrays;
import java.util.List;

@Component
public class ConfirmAction extends BaseAdCreationAction {

    public ConfirmAction(TelegramSender<SendMessage> telegramSender) {
        super(
                "Поздравляю! объявление успешно создано. Через некоторое время оно будет опубликовано в канале",
                telegramSender
        );
    }

    @Override
    public void execute(StateContext<AdCreationState, AddAdvertisementEvent> context) {
        StateMachine<?, ?> sm = getStateMachine(context);
        Long chatId = StateMachineContextHelper.getChatId(sm);
        fillInAd(sm);
        SendMessage message = createMessage(chatId, ACTION_TEXT, null);
        telegramSender.send(message);
    }

    @Override
    protected void fillInAd(StateMachine<?, ?> stateMachine) {
        String previousStageText = StateMachineContextHelper.getPreviousStageText(stateMachine);
        List<String> attributes = Arrays.stream(previousStageText.split("\\n")).toList();
        AdvertisementDraftDto adDraft = StateMachineContextHelper.getAdDraft(stateMachine);
        adDraft.setAttributes(attributes);
        StateMachineContextHelper.setAdDraft(stateMachine, adDraft);
    }
}
