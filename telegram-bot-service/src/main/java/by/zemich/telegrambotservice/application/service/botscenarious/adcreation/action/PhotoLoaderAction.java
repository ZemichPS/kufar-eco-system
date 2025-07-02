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

@Component
public class PhotoLoaderAction extends BaseAdCreationAction {

    public PhotoLoaderAction(TelegramSender<SendMessage> telegramSender) {
        super("выберите фото", telegramSender);
    }

    @Override
    public void execute(StateContext<AdCreationState, AddAdvertisementEvent> context) {
        StateMachine<?, ?> sm = context.getStateMachine();
        fillInAd(sm);
        Long chatId = StateMachineContextHelper.getChatId(sm);
        SendMessage message = createMessage(chatId, this.ACTION_TEXT, null);
        telegramSender.send(message);
    }

    @Override
    protected void fillInAd(StateMachine<?, ?> stateMachine) {
        String comment = StateMachineContextHelper.getPreviousStageText(stateMachine);
        AdvertisementDraftDto adDraft = StateMachineContextHelper.getAdDraft(stateMachine);
        adDraft.setComment(comment);
        StateMachineContextHelper.setAdDraft(stateMachine, adDraft);
    }
}
