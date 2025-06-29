package by.zemich.telegrambotservice.application.service.commands;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StateProcessorHandler {

    private Map<String, StateProcessor> processorMap = new HashMap<>();

    public void register(String state, StateProcessor processor) {
        processorMap.put(state, processor);
    }

    public StateProcessor getProcessorByState(String state) {
        return processorMap.getOrDefault(state, new StateProcessor() {
            @Override
            public String processState() {
                return "";
            }

            @Override
            public void proceed(String chatId) {

            }
        });
    }
}
