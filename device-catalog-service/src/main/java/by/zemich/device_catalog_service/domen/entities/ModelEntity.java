package by.zemich.device_catalog_service.domen.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity()
@Table(name = "models", schema = "app")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelEntity {
    @Id
    private UUID uuid;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_uuid", referencedColumnName = "uuid")
    private BrandEntity brand;

    public ModelEntity(String name) {
        this.name = name;
    }

    public ModelEntity(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }
}
