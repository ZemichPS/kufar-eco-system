package by.zemich.telegrambotservice.application.service.scenarious.registration.state;

public enum UserRegistrationState {
    START_REGISTRATION,
    CONFIRM_REGISTRATION,
    ROLE_INPUT,
    USER_DATA_INPUT,
    USER_CONTACT_INPUT,
    END_REGISTRATION, START_REGISTRATION_ERROR, USER_EXISTS, ROLE_INPUT_ERROR, GREETING, NOTIFY_ABOUT_EXISTING_USER,
}
