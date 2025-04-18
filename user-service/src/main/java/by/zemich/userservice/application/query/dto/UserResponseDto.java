package by.zemich.userservice.application.query.dto;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserResponseDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

}
