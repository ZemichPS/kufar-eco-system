package by.zemich.advertisementservice.domain.entity;


import by.zemich.advertisementservice.domain.valueobject.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class User {
    private Id userId;

    public User(Id userId) {
        this.userId = userId;
    }
}
