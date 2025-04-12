package by.zemich.userservice.dao.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "organizations", schema = "app")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private Address address;
    @Enumerated(EnumType.STRING)
    private Type type;
    private String phoneNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;
}

@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class Address {
    private String postalCode;
    private String region;
    private String district;        // Район (если применимо)
    private String city;
    private String street;
    private String houseNumber;
    private String apartmentNumber;
}

@Getter
enum Type {
    SERVICE_CENTER("Сервисный центр"),
    WORKSHOP("Мастерская"),
    SELF_EMPLOYED("Самозанятый");

    private final String title;

    Type(String title) {
        this.title = title;
    }
}