package by.zemich.telegrambotservice.application.service.scenarious.api;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class KeyboardUtil {

    public static InlineKeyboardMarkup createInlineKeyboardMarkup(List<String> itemList, int buttonCountInRow) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (int i = 0; i < itemList.size(); i += buttonCountInRow) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(createInlineKeyboardButton(itemList.get(i)));
            if (i + 1 < itemList.size()) {
                row.add(createInlineKeyboardButton(itemList.get(i + 1)));
            }
            rows.add(row);
        }
        return InlineKeyboardMarkup.builder()
                .keyboard(rows)
                .build();

    }

    public static InlineKeyboardMarkup createInlineKeyboardMarkup(List<String> itemList, List<String> callBackList ) {
        if(itemList.size() != callBackList.size()) throw new IllegalArgumentException("Item list and callback data list sizes must be equal");
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (int i = 0; i < itemList.size(); i++) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(createInlineKeyboardButton(itemList.get(i), callBackList.get(i)));
            if (i + 1 < itemList.size()) {
                row.add(createInlineKeyboardButton(itemList.get(i + 1)));
            }
            rows.add(row);
        }
        return InlineKeyboardMarkup.builder()
                .keyboard(rows)
                .build();

    }

    private InlineKeyboardButton createInlineKeyboardButton(String text) {
        return InlineKeyboardButton.builder()
                .text(text)
                .callbackData(text)
                .build();
    }

    private InlineKeyboardButton createInlineKeyboardButton(String text, String callBackData) {
        return InlineKeyboardButton.builder()
                .text(text)
                .callbackData(callBackData)
                .build();
    }
}
