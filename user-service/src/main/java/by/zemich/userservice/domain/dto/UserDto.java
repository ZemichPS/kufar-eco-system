package by.zemich.userservice.domain.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Getter
@Setter
public class UserDto {
    private UUID id;
    private String firstName;
    private String password;
    private String surname;
    private String postalCode;
    private String region;
    private String district;        // Район (если применимо)
    private String city;
    private String street;
    private String houseNumber;
    private String apartmentNumber;
    private String email;
    private String telegramUserId;
    @OneToMany
    private List<PhoneNumberDto> phoneNumbers;

    @Data
    class PhoneNumberDto {
        @Pattern(regexp = "^\\+375\\d{9}$", message = "Номер телефона должен начинаться с +375 и содержать 9 цифр")
        private String phoneNumber;
    }
}

