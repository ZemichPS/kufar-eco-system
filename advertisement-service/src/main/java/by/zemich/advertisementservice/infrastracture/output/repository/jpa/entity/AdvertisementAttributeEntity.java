package by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity;

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

    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertisement_uuid", referencedColumnName = "uuid")
    private AdvertisementEntity advertisement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_attribute_uuid", referencedColumnName = "uuid")
    private CategoryAttributeEntity categoryAttribute;

}
