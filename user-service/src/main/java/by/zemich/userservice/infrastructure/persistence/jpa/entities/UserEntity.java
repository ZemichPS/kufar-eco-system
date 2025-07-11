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
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Role role;
    @CreationTimestamp
    private LocalDateTime registrationDate;
    private String email;
    private Long telegramUserId;
    private Long telegramChatId;
    private String telegramUsername;
    private String phoneNumber;
    private String password;
    @OneToOne(
            fetch = FetchType.EAGER,
            mappedBy = "owner",
            cascade = CascadeType.ALL
    )
    private OrganizationEntity organization;
    private Boolean enabled;


    @Getter
    public enum Role {
        OWNER("владелец"), STAFF("сотрудник"), ADMIN("администратор");
        private String title;

        Role(String title) {
            this.title = title;
        }
    }

}
