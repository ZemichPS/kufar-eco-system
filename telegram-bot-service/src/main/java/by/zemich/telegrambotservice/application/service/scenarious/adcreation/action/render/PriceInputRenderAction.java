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

@Component
public class PriceInputRenderAction extends AdCreationRenderAction {

    public PriceInputRenderAction(TelegramSender<SendMessage> telegramSender) {
        super("Введите цену", telegramSender);
    }

    @Override
    public void execute(StateContext<AdCreationState, AddAdvertisementEvent> context) {
        StateMachine<AdCreationState, AddAdvertisementEvent> sm = context.getStateMachine();
        fillInAd(sm);
        Long chatId = StateMachineContextHelper.getChatId(context);
        SendMessage message = createMessage(chatId, ACTION_TEXT, null);
        this.telegramSender.send(message);
    }

    protected void fillInAd(StateMachine<AdCreationState, AddAdvertisementEvent> stateMachine) {
        String condition = StateMachineContextHelper.getPreviousStageText(stateMachine);
        AdvertisementDraftDto adDraft = StateMachineContextHelper.getAdDraft(stateMachine);
        adDraft.setCondition(condition);
        StateMachineContextHelper.setAdDraft(stateMachine, adDraft);
    }

    @Override
    public AdCreationState getHandleState() {
        return AdCreationState.PRICE_INPUT;
    }
}
