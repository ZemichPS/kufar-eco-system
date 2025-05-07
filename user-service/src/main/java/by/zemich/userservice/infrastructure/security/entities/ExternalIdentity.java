package by.zemich.userservice.infrastructure.security.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(schema = "app", name = "identities")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ExternalIdentity {
    @Id
    private UUID id;
    private String providerName;
    private String providerUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserDetailsImpl userDetails;

}
