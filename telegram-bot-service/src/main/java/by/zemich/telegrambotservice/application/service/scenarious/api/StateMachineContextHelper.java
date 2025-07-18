package by.zemich.telegrambotservice.application.service.scenarious.api;

import by.zemich.telegrambotservice.application.service.scenarious.registration.event.UserRegistrationEvent;
import by.zemich.telegrambotservice.application.service.scenarious.registration.state.UserRegistrationState;
import by.zemich.telegrambotservice.domain.dto.AdvertisementDraftDto;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.telegram.telegrambots.meta.api.objects.Update;

@SuppressWarnings("unchecked")
public class StateMachineContextHelper {

    public static <E extends Enum<E>> E getNextEvent(StateMachine<?, E> sm) {
        return (E) sm.getExtendedState().get("nextEvent", Enum.class);
    }

    public static Long getChatId(StateContext<?, ?> context) {
        return context.getExtendedState().get("chatId", Long.class);
    }

    public static String getPreviousStageText(StateMachine<?, ?> sm) {
        return sm.getExtendedState().get("textFromUpdate", String.class);
    }

    public static void setAdDraft(StateMachine<?, ?> sm, AdvertisementDraftDto adDraft) {
        sm.getExtendedState().getVariables().put(adDraft, adDraft);
    }

    public static AdvertisementDraftDto getAdDraft(StateMachine<?, ?> sm) {
        return sm.getExtendedState().get("adDraft", AdvertisementDraftDto.class);
    }

    public static Update getUpdate(StateMachine<?, ?> sm) {
        return sm.getExtendedState().get("update", Update.class);
    }

    public static void setUpdate(StateMachine<?, ?> sm, Update update) {
        sm.getExtendedState().getVariables().put("update", update);
    }

    public static void setChatId(StateMachine<?, ?> sm, Long chatId) {
        sm.getExtendedState().getVariables().put("chaId", chatId);
    }

    public static void setVariable(StateMachine<?, ?> sm, String name, Object variable) {
        sm.getExtendedState().getVariables().put(name, variable);
    }



    public static <T> T getVariable(StateMachine<?, ?> sm, String name, Class<T> type) {
        Object value = sm.getExtendedState().getVariables().get(name);
        if (!type.isInstance(value)) {
            throw new RuntimeException(
                    String.format("Type mismatch for variable '%s'. Expected %s but got %s",
                            name, type.getSimpleName(), value.getClass().getSimpleName())
            );
        }
        return (T) value;
    }

    public static <E extends Enum<E>> E parseEventFromString(StateMachine<?, E> sm, String input) {
        Class<E> clazz = (Class<E>) sm.getExtendedState().get("eventClass", Class.class);
        return Enum.valueOf(clazz, input);
    }

    public static void setUserId(StateMachine<?, ?> sm, Long userId) {
        sm.getExtendedState().getVariables().put("userId", userId);
    }

    public static Long getUserId(StateMachine<?, ?> sm) {
        return sm.getExtendedState().get("userId", Long.class);
    }
}
