package by.zemich.advertisementservice.infrastracture.ouput.repository.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "advertisement_attributes", schema = "app")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdvertisementAttributeEntity {
    @Id
    private UUID uuid;
    @ManyToOne
    private AdvertisementEntity advertisement;
    @ManyToOne
    private CategoryAttributeEntity categoryAttribute;
    private String value;
}
