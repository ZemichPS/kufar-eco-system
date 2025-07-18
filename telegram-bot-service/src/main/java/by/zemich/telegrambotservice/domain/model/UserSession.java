package by.zemich.telegrambotservice.domain.model;

import by.zemich.telegrambotservice.application.service.scenarious.ScenarioType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserSession implements Serializable {
    private UUID id;
    Long chatId;
    Long userId;
    ScenarioType currentScenarioType;
    private Map<String, Object> contextData = new HashMap<>();
    private UUID stateMachineId; // Связь с SM в Redis
    private LocalDateTime lastActivity;
}
