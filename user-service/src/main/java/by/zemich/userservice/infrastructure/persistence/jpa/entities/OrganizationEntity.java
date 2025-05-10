package by.zemich.userservice.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "organizations", schema = "app")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class OrganizationEntity {
    @Id
    private UUID id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private UserEntity owner;
    private String name;
    private String specialization;
    private String organizationType;
    private String phoneNumber;
    @Embedded
    private Address address;
}
