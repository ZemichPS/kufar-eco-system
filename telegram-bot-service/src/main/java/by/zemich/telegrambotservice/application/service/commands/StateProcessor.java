package by.zemich.telegrambotservice.application.service.commands;

import org.springframework.beans.factory.annotation.Autowired;

public interface StateProcessor {

    String processState();

    @Autowired
    default void regMe(StateProcessorHandler handler){
        handler.register(processState(), this);
    }

    void proceed(String chatId);
}
