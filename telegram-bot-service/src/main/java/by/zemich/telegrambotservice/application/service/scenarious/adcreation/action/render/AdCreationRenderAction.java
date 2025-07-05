package by.zemich.telegrambotservice.application.service.scenarious.adcreation.action.render;

import by.zemich.telegrambotservice.application.service.api.TelegramSender;
import by.zemich.telegrambotservice.application.service.scenarious.adcreation.AdCreationState;
import by.zemich.telegrambotservice.application.service.scenarious.adcreation.AddAdvertisementEvent;
import by.zemich.telegrambotservice.application.service.scenarious.api.BaseRenderAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public abstract class AdCreationRenderAction extends BaseRenderAction<AdCreationState, AddAdvertisementEvent> {


    public AdCreationRenderAction(String ACTION_TEXT, TelegramSender<SendMessage> telegramSender) {
        super(ACTION_TEXT, telegramSender);
    }


}
