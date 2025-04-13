package by.zemich.userservice.domain.models.user.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserId {
    private UUID id;
}
