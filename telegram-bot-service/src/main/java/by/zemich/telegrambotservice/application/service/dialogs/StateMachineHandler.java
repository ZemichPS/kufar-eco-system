package by.zemich.telegrambotservice.application.service.dialogs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StateMachineHandler {

    private final Map<String, StateMachineFactory<?,?>> stateMachineFactories;

    public StateMachineHandler(Map<String, StateMachineFactory<?,?>> stateMachineFactories) {
        this.stateMachineFactories = stateMachineFactories;
    }

    public StateMachineFactory<?,?> getByDialogType(DialogType dialogType) {
        return stateMachineFactories.computeIfAbsent(
                dialogType.name(),
                dialog -> stateMachineFactories.get("NONE")
        );
    }


}
