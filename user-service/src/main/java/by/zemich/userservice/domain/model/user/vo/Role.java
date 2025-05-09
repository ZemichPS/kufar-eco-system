package by.zemich.userservice.domain.model.user.vo;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("администратор"),
    MODERATOR("модератор"),
    OWNER("владелец"),
    SELLER("продавец"),
    STAFF("сотрудник");
    private String title;

    Role(String title) {
        this.title = title;
    }

}
