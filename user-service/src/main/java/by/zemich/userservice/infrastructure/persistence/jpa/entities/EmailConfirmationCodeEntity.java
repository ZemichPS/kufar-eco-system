package by.zemich.userservice.infrastructure.persistence.jpa.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "confirmation_codes", schema = "app")
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor @Builder
public class EmailConfirmationCodeEntity {
    @Id
    @GeneratedValue
    private UUID uuid;

    private UUID userUuid;

    private String email;

    private String code;

    private LocalDateTime expiresAt;

    private boolean confirmed;
}
