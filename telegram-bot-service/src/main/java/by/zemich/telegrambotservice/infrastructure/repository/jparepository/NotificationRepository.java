package by.zemich.telegrambotservice.infrastructure.repository.jparepository;

import by.zemich.telegrambotservice.domain.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

}
