package by.zemich.telegrambotservice.application.service.botscenarious.adcreation;

import by.zemich.telegrambotservice.application.service.botscenarious.ScenarioEvent;

public enum AddAdvertisementEvent implements ScenarioEvent {
    CATEGORY_RECEIVED,
    CONDITION_RECEIVED,
    PRICE_RECEIVED,
    COMMENT_RECEIVED,
    PHOTO_RECEIVED,
    ATTRIBUTES_RECEIVED,
    CONFIRM
}
