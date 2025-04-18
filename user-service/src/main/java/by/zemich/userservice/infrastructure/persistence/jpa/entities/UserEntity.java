package by.zemich.userservice.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(schema = "app", name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {
    @Id
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Role role;
    @CreationTimestamp
    private LocalDateTime registeredAt;
    private String email;
    private String telegramUserId;
    private String phoneNumber;
    private String password;
    private UUID organizationId;

    @Getter
    public enum Role {
        OWNER("владелец"), STAFF("сотрудник"), ADMIN("администратор");
        private String title;

        Role(String title) {
            this.title = title;
        }
    }

}
