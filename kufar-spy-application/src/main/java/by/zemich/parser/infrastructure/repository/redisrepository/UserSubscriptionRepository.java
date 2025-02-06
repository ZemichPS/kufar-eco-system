package by.zemich.parser.infrastructure.repository.redisrepository;

import by.zemich.parser.domain.model.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, UUID> {
}
