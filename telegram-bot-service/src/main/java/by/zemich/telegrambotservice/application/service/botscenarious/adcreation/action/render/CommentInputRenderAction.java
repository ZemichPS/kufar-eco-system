package by.zemich.telegrambotservice.application.service.botscenarious.adcreation.action.render;

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
public class CommentInputRenderAction extends AdCreationRenderAction {

    public CommentInputRenderAction(TelegramSender<SendMessage> telegramSender) {
        super("Добавьте комментарий, например некоторые особенности состояния и прочее", telegramSender);
    }

    @Override
    public void execute(StateContext<AdCreationState, AddAdvertisementEvent> context) {
        StateMachine<AdCreationState, AddAdvertisementEvent> sm = context.getStateMachine();
        fillInAd(sm);
        Long chatId = StateMachineContextHelper.getChatId(sm);
        SendMessage message = createMessage(chatId, ACTION_TEXT, null);
        this.telegramSender.send(message);
    }

    @Override
    protected void fillInAd(StateMachine<AdCreationState, AddAdvertisementEvent> stateMachine) {
        String price = StateMachineContextHelper.getPreviousStageText(stateMachine);
        AdvertisementDraftDto adDraft = StateMachineContextHelper.getAdDraft(stateMachine);
        adDraft.setPrice(price);
        StateMachineContextHelper.setAdDraft(stateMachine, adDraft);
    }
}
