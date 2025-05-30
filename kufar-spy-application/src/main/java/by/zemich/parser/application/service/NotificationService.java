package by.zemich.parser.application.service;

import by.zemich.parser.application.service.api.NotificationPostManager;
import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.domain.model.Notification;
import by.zemich.parser.domain.model.User;
import by.zemich.parser.infrastructure.repository.jparepository.NotificationRepository;
import by.zemich.parser.application.service.api.Notifiable;
import by.zemich.parser.application.service.api.PhotoMessenger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;
    private final PostManager postManager;
    private final NotificationPostManager<SendPhoto, Notification> notificationNotificationPostManager;
    private final PhotoMessenger<SendPhoto> telegramPhotoMessenger;
    private final List<Notifiable> notifiable;

    public void notifyUserMatchingAd(UUID userId, Advertisement advertisement) {
        Long chatId = userService.getById(userId).map(User::getTelegramChatId).orElseThrow();
        SendPhoto adPost = postManager.create(advertisement);
        adPost.setChatId(chatId.toString());
        telegramPhotoMessenger.sendPhoto(adPost);
    }

    public UUID notifyUserById(UUID userId, Notification notification) {
        User user = userService
                .getById(userId)
                .orElseThrow();
        notifyAndSave(notification, user);
        return notification.getId();
    }

    public UUID notifyAllUsers(Notification notification) {
        userService.getAll().forEach(
                user -> notifyAndSave(notification, user)
        );
        return notification.getId();
    }

    public UUID notifyAll(Notification notification) {
        notifiable.forEach(
                notifiable -> notifyAndSave(notification, notifiable)
        );
        return notification.getId();
    }

    public UUID notifyById(Notification notification, String notifiableId) {
        final Notifiable foundedNotifiable = notifiable.stream()
                .filter(notifiable -> notifiable.getNotifierId().equals(notifiableId))
                .findFirst()
                .orElseThrow();
        notifyAndSave(notification, foundedNotifiable);
        return notification.getId();
    }

    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }

    public Notification getById(UUID id) {
        // TODO создать исключение и выбрасывать его ??????
        return notificationRepository.findById(id).orElseThrow();
    }

    private void notifyAndSave(Notification notification, Notifiable notifiable) {
        notificationRepository.save(notification);
        notifiable.notify(notification);
        notification.setPublishedAt(LocalDateTime.now());
        notificationRepository.save(notification);
    }

    private void notifyAndSave(Notification notification, User user) {
        SendPhoto adPost = notificationNotificationPostManager.create(notification);
        adPost.setChatId(user.getTelegramChatId().toString());
        telegramPhotoMessenger.sendPhoto(adPost);
    }


}
