package by.zemich.telegrambotservice.application.service.botscenarious.adcreation.action.render;

import by.zemich.telegrambotservice.application.service.api.TelegramSender;
import by.zemich.telegrambotservice.application.service.botscenarious.adcreation.AdCreationState;
import by.zemich.telegrambotservice.application.service.botscenarious.adcreation.AddAdvertisementEvent;
import by.zemich.telegrambotservice.application.service.botscenarious.api.KeyboardUtil;
import by.zemich.telegrambotservice.application.service.botscenarious.api.StateMachineContextHelper;
import by.zemich.telegrambotservice.application.service.openfeign.DeviceCatalogDeviceOpenFeign;
import by.zemich.telegrambotservice.domain.dto.AdvertisementDraftDto;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

@Component
public class ModelChoiceAction extends AdCreationRenderAction {

    private final DeviceCatalogDeviceOpenFeign deviceCatalogDeviceOpenFeign;

    public ModelChoiceAction(TelegramSender<SendMessage> telegramSender, DeviceCatalogDeviceOpenFeign deviceCatalogDeviceOpenFeign) {
        super("Выберите модель", telegramSender);
        this.deviceCatalogDeviceOpenFeign = deviceCatalogDeviceOpenFeign;
    }

    @Override
    public void execute(StateContext<AdCreationState, AddAdvertisementEvent> context) {
        Long chatId = StateMachineContextHelper.getChatId(context.getStateMachine());
        String vendorName = getVendor(context.getStateMachine());
        List<String> modelList = deviceCatalogDeviceOpenFeign.getModelByVendor(vendorName);
        InlineKeyboardMarkup modelInlineKeyboardMarkup = KeyboardUtil.createInlineKeyboardMarkup(modelList, 3);
        SendMessage sendMessage = createMessage(chatId, this.ACTION_TEXT, modelInlineKeyboardMarkup);
        telegramSender.send(sendMessage);
    }

    private String getVendor(StateMachine<AdCreationState, AddAdvertisementEvent> stateMachine) {
        AdvertisementDraftDto adDraft = StateMachineContextHelper.getAdDraft(stateMachine);
        return adDraft.getVendorName();
    }
}
