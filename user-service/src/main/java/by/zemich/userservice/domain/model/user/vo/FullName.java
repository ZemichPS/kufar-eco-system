package by.zemich.userservice.domain.model.user.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FullName {
    private String firstname;
    private String lastname;
}
