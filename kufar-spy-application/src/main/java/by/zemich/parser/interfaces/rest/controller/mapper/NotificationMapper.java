package by.zemich.parser.interfaces.controller.mapper;

import by.zemich.parser.domain.model.Notification;
import by.zemich.parser.interfaces.controller.dto.request.NotificationDto;
import by.zemich.parser.interfaces.controller.dto.response.NotificationResponseDto;

public class NotificationMapper {
    public static Notification toEntity(NotificationDto notificationDto) {
        return Notification.builder()
                .title(notificationDto.getTitle())
                .content(notificationDto.getMessage())
                .build();
    }

    public static NotificationResponseDto toResponseDto(Notification notification) {
        return NotificationResponseDto.builder()
                .title(notification.getTitle())
                .content(notification.getContent())
                .imageName(notification.getImageName())
                .build();
    }
}
