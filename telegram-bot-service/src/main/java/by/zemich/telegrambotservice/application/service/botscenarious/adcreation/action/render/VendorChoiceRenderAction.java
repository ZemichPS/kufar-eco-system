package by.zemich.telegrambotservice.application.service.botscenarious.adcreation.action.render;

import by.zemich.telegrambotservice.application.service.api.TelegramSender;
import by.zemich.telegrambotservice.application.service.botscenarious.adcreation.AdCreationState;
import by.zemich.telegrambotservice.application.service.botscenarious.adcreation.AddAdvertisementEvent;
import by.zemich.telegrambotservice.application.service.botscenarious.api.KeyboardUtil;
import by.zemich.telegrambotservice.application.service.botscenarious.api.StateMachineContextHelper;
import by.zemich.telegrambotservice.application.service.openfeign.DeviceCatalogDeviceOpenFeign;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

@Component
public class VendorChoiceRenderAction extends AdCreationRenderAction {

    private final DeviceCatalogDeviceOpenFeign deviceCatalogDeviceOpenFeign;

    public VendorChoiceRenderAction(TelegramSender<SendMessage> telegramSender, DeviceCatalogDeviceOpenFeign deviceCatalogDeviceOpenFeign) {
        super("Выберите производителя", telegramSender);
        this.deviceCatalogDeviceOpenFeign = deviceCatalogDeviceOpenFeign;
    }

    @Override
    public void execute(StateContext<AdCreationState, AddAdvertisementEvent> context) {
        List<String> vendors = deviceCatalogDeviceOpenFeign.getVendors();
        Long chatId = StateMachineContextHelper.getChatId(context.getStateMachine());
        InlineKeyboardMarkup vendorInlineKeyboardMarkup = KeyboardUtil.createInlineKeyboardMarkup(vendors, 3);
        SendMessage sendMessage = createMessage(chatId, this.ACTION_TEXT, vendorInlineKeyboardMarkup);
        telegramSender.send(sendMessage);
    }

}
