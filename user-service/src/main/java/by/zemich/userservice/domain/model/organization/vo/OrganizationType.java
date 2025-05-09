package by.zemich.userservice.domain.model.organization.vo;

import lombok.Getter;

@Getter
public enum OrganizationType {
    SERVICE_CENTER("Сервисный центр"),
    WORKSHOP("Мастерская"),
    SELF_EMPLOYED("Самозанятый на дому");

    private final String title;

    OrganizationType(String title) {
        this.title = title;
    }
}
