package by.zemich.userservice.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "organizations", schema = "app")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class OrganizationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private UserEntity owner;
    private String name;
    @Embedded
    private Address address;
    private String organizationType;
    private String phoneNumber;
    private String specialization;

    @ElementCollection
    @CollectionTable(name = "organization_staff", joinColumns = @JoinColumn(name = "organization_id"))
    @Column(name = "staff_user_id")
    private List<UUID> staff = new ArrayList<>();
}
