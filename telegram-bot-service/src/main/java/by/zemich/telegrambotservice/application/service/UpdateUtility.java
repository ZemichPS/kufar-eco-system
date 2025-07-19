package by.zemich.telegrambotservice.application.service;

import by.zemich.telegrambotservice.application.service.scenarious.registration.event.UserRegistrationEvent;
import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@UtilityClass
public class UpdateUtility {

    public static Optional<String> getUsername(Update update) {
        String username = null;
        if (update.hasMessage()) {
            username = update.getMessage().getFrom().getUserName();
        } else if (update.hasCallbackQuery()) {
            username = update.getCallbackQuery().getFrom().getUserName();
        } else if (update.hasInlineQuery()) {
            username = update.getInlineQuery().getFrom().getUserName();
        } else if (update.hasChosenInlineQuery()) {
            username = update.getChosenInlineQuery().getFrom().getUserName();
        } else if (update.hasMyChatMember()) {
            username = update.getMyChatMember().getFrom().getUserName();
        }
        return Optional.ofNullable(username);
    }

    public static Optional<String> getUserFirstname(Update update) {
        String firsname = null;
        if (update.hasMessage()) {
            firsname = update.getMessage().getFrom().getFirstName();
        } else if (update.hasCallbackQuery()) {
            firsname = update.getCallbackQuery().getFrom().getFirstName();
        } else if (update.hasInlineQuery()) {
            firsname = update.getInlineQuery().getFrom().getFirstName();
        } else if (update.hasChosenInlineQuery()) {
            firsname = update.getChosenInlineQuery().getFrom().getFirstName();
        } else if (update.hasMyChatMember()) {
            firsname = update.getMyChatMember().getFrom().getFirstName();
        }
        return Optional.ofNullable(firsname);
    }

    public static Optional<String> getUserLastname(Update update) {
        String lastname = null;
        if (update.hasMessage()) {
            lastname = update.getMessage().getFrom().getFirstName();
        } else if (update.hasCallbackQuery()) {
            lastname = update.getCallbackQuery().getFrom().getFirstName();
        } else if (update.hasInlineQuery()) {
            lastname = update.getInlineQuery().getFrom().getFirstName();
        } else if (update.hasChosenInlineQuery()) {
            lastname = update.getChosenInlineQuery().getFrom().getFirstName();
        } else if (update.hasMyChatMember()) {
            lastname = update.getMyChatMember().getFrom().getFirstName();
        }
        return Optional.ofNullable(lastname);
    }


    public static Optional<Long> getUserId(Update update) {
        Long userId = null;
        if (update.hasMessage()) {
            userId = update.getMessage().getFrom().getId();
        } else if (update.hasCallbackQuery()) {
            userId = update.getCallbackQuery().getFrom().getId();
        } else if (update.hasInlineQuery()) {
            userId = update.getInlineQuery().getFrom().getId();
        } else if (update.hasChosenInlineQuery()) {
            userId = update.getChosenInlineQuery().getFrom().getId();
        } else if (update.hasMyChatMember()) {
            userId = update.getMyChatMember().getFrom().getId();
        }
        return Optional.ofNullable(userId);
    }

    public static Optional<Long> getChatId(Update update) {
        Long chatId = null;
        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
        } else if (update.hasMyChatMember()) {
            chatId = update.getMyChatMember().getChat().getId();
        }
        return Optional.ofNullable(chatId);
    }

    @SuppressWarnings("unchecked")
    public static <E extends Enum<E>> Optional<E> extractEventFromCallback(Update update, Class<E> eventClazz) {
        if (update.hasCallbackQuery()) {
            String callback = update.getCallbackQuery().getData();
            if (callback.startsWith("event:")) {
                String rawEvent = callback.toUpperCase().substring("event:".length());
                E event = (E) Enum.valueOf(UserRegistrationEvent.class, rawEvent);
                return Optional.of(event);
            }
        }
        return Optional.empty();
    }


}
