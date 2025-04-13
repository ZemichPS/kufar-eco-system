package by.zemich.userservice.domain.models.user.vo;

import lombok.Getter;

@Getter
public enum Role {
    OWNER("владелец"), STAFF("сотрудник");
    private String title;

    Role(String title) {
        this.title = title;
    }

}
