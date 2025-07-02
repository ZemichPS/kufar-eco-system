package by.zemich.telegrambotservice.application.service.botscenarious.adcreation.action;

import by.zemich.telegrambotservice.application.service.api.TelegramSender;
import by.zemich.telegrambotservice.application.service.botscenarious.adcreation.AdCreationState;
import by.zemich.telegrambotservice.application.service.botscenarious.adcreation.AddAdvertisementEvent;
import by.zemich.telegrambotservice.application.service.botscenarious.api.KeyboardUtil;
import by.zemich.telegrambotservice.application.service.botscenarious.api.StateMachineContextHelper;
import by.zemich.telegrambotservice.application.service.openfeign.AdvertisementServiceOpenFeign;
import by.zemich.telegrambotservice.application.service.openfeign.UserServiceOpenFeign;
import by.zemich.telegrambotservice.domain.dto.AdvertisementDraftDto;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.UUID;

@Component
public class CategoryChoiceAction extends BaseAdCreationAction {

    private final AdvertisementServiceOpenFeign advertisementServiceOpenFeign;
    private final UserServiceOpenFeign userServiceOpenFeign;

    public CategoryChoiceAction(TelegramSender<SendMessage> telegramSender,
                                AdvertisementServiceOpenFeign advertisementServiceOpenFeign,
                                UserServiceOpenFeign userServiceOpenFeign
    ) {
        super("Выберите категорию", telegramSender);
        this.advertisementServiceOpenFeign = advertisementServiceOpenFeign;
        this.userServiceOpenFeign = userServiceOpenFeign;
    }


    @Override
    public void execute(StateContext<AdCreationState, AddAdvertisementEvent> stateContext) {
        StateMachine<?, ?> sm = stateContext.getStateMachine();
        fillInAd(sm);
        Long chatId = StateMachineContextHelper.getChatId(sm);
        List<String> categoryList = advertisementServiceOpenFeign.getCategories();
        InlineKeyboardMarkup inlineKeyboardMarkup = KeyboardUtil.createInlineKeyboardMarkup(categoryList, 2);
        SendMessage sendMessage = createMessage(chatId, ACTION_TEXT, inlineKeyboardMarkup);
        telegramSender.send(sendMessage);
    }

    @Override
    protected void fillInAd(StateMachine<?, ?> stateMachine) {
        Long userId = StateMachineContextHelper.getUserId(stateMachine);
        UUID userUuid = userServiceOpenFeign.getUserIdByTelegramId(userId);
        AdvertisementDraftDto adDraft = new AdvertisementDraftDto(userUuid);
        StateMachineContextHelper.setAdDraft(stateMachine, adDraft);
    }
}
