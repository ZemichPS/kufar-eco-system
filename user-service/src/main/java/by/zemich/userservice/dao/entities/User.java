package by.zemich.userservice.dao.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "app")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @CreationTimestamp(source = SourceType.VM)
    private LocalDateTime registeredAt;
    @Embedded
    private String firstName;
    private String surname;
    private String email;
    private String phoneNumber;
    private String telegramUserId;
    private String password;
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "owner"
    )
    private List<Organization> organizations;

    public void addOrganization(Organization organization) {
        organization.setOwner(this);
        organizations.add(organization);
    }

    public void removeOrganization(Organization organization) {
        organization.setOwner(null);
        organizations.remove(organization);
    }

}

