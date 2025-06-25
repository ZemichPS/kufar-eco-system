package by.zemich.telegrambotservice.application.service.dialogs.FSM;

public enum AdCreationStates {
    START,
    CATEGORY_INPUT,
    CONDITION_INPUT,
    PRICE_INPUT,
    COMMENT_INPUT,
    PHOTO_INPUT,
    ATTRIBUTES_INPUT,
    CONFIRMATION,
    COMPLETED
}
