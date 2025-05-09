package by.zemich.userservice.api.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class OrganizationRequestDto {
    @NotNull(message = "ID владельца обязательно для заполнения")
    private UUID ownerId;

    @NotBlank(message = "Название организации не может быть пустым")
    @Size(min = 2, max = 100, message = "Название должно быть от 2 до 100 символов")
    private String name;

    @NotBlank(message = "Тип организации не может быть пустым")
    private String organizationType;

    @NotBlank(message = "Номер телефона обязателен")
    @Pattern(
            regexp = "^\\+375 (44|33|25|29|17)\\d{7}$",
            message = "Номер должен быть в формате: +375 44XXXXXXX (коды: 44, 33, 25, 29, 17)"
    )
    private String phoneNumber;

    @Size(max = 500, message = "Специализация не должна превышать 500 символов")
    private String specialization;

    @NotBlank(message = "Почтовый индекс обязателен")
    @Pattern(regexp = "^[0-9]{6}$",
            message = "Индекс должен состоять из 6 цифр")
    private String postalCode;

    @NotBlank(message = "Регион обязателен")
    private String region;

    private String district;  // Необязательное поле

    @NotBlank(message = "Город обязателен")
    private String city;

    @NotBlank(message = "Улица обязательна")
    private String street;

    @NotBlank(message = "Номер дома обязателен")
    private String houseNumber;

    private String apartmentNumber;  // Необязательное поле
}
