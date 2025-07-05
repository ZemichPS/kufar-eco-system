package by.zemich.telegrambotservice.application.service.scenarious.adcreation.action.render;

import by.zemich.telegrambotservice.application.service.api.TelegramSender;
import by.zemich.telegrambotservice.application.service.scenarious.adcreation.AdCreationState;
import by.zemich.telegrambotservice.application.service.scenarious.adcreation.AddAdvertisementEvent;
import by.zemich.telegrambotservice.application.service.scenarious.api.KeyboardUtil;
import by.zemich.telegrambotservice.application.service.scenarious.api.StateMachineContextHelper;
import by.zemich.telegrambotservice.application.service.openfeign.DeviceCatalogDeviceOpenFeign;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

@Component
public class ManufacturerChoiceRenderAction extends AdCreationRenderAction {

    private final DeviceCatalogDeviceOpenFeign deviceCatalogDeviceOpenFeign;

    public ManufacturerChoiceRenderAction(TelegramSender<SendMessage> telegramSender, DeviceCatalogDeviceOpenFeign deviceCatalogDeviceOpenFeign) {
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

    @Override
    public AdCreationState getHandleState() {
        return AdCreationState.MANUFACTURER_INPUT;
    }

}
