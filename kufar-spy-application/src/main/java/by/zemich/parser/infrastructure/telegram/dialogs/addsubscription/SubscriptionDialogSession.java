package by.zemich.parser.infrastructure.telegram.dialogs.addsubscription;

import by.zemich.parser.domain.model.UserSubscription;
import by.zemich.parser.infrastructure.telegram.sessions.AbstractDialogSession;
import by.zemich.parser.domain.model.criterias.Criteria;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@RedisHash("subscriptionDialogSession")
public class SubscriptionDialogSession extends AbstractDialogSession<Criteria> {

    public SubscriptionDialogSession(UUID id, UUID userID, Long expiresIn) {
        super(id, userID);
    }

    public SubscriptionDialogSession(UUID userID) {
        super(UUID.randomUUID(),
                userID
        );
    }

    // TODO рассмотреть возможность удаления
    public UserSubscription createUserSubscription() {
        UserSubscription userSubscription = new UserSubscription(UUID.randomUUID(), getUserId());
        criteriaMap.forEach((key, value) -> {
            userSubscription.addCriteria(value);
        });
        return userSubscription;
    }
}
