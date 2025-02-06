package by.zemich.parser.application.service;

import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.domain.model.UserSubscription;
import by.zemich.parser.infrastructure.repository.redisrepository.UserSubscriptionRepository;
import by.zemich.parser.application.service.api.AdvertisementPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionManager implements AdvertisementPublisher {
    private final UserSubscriptionRepository subscriptionRepository;
    private final NotificationService notificationService;

    public void matchAndNotify(Advertisement advertisement) {
        subscriptionRepository.findAll().stream()
                .filter(subscription -> subscription.isSatisfied(advertisement))
                .map(UserSubscription::getSubscriberId)
                .forEach(userId-> notificationService.notifyUserMatchingAd(userId, advertisement));
    }

    public void subscribe(UserSubscription subscription) {
        subscriptionRepository.save(subscription);
    }

    public void unsubscribe(UUID id) {
        subscriptionRepository.deleteById(id);
    }

    @Override
    public boolean publish(Advertisement advertisement) {
        this.matchAndNotify(advertisement);
        return true;
    }
}
