package by.zemich.userservice.domain.model.user.vo;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("администратор"),
    MODERATOR("модератор"),
    BUSINESS_OWNER("владелец"),
    SELLER("продавец");
    private String title;

    Role(String title) {
        this.title = title;
    }

}
