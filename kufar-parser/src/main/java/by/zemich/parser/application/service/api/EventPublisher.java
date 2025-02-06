package by.zemich.parser.application.service.api;

import by.zemich.parser.domain.model.Advertisement;

public interface EventPublisher {
    void publish(Advertisement event);
}
