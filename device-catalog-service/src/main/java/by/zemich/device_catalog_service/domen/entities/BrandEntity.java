package by.zemich.device_catalog_service.domen.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@Table(name = "brands", schema = "app")
@NoArgsConstructor
@AllArgsConstructor
public class BrandEntity {
    @Id
    private UUID uuid;
    @Column(nullable = false, unique = true)
    private String name;
    @OneToMany(
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "brand"
    )
    @Getter(AccessLevel.NONE)
    private List<ModelEntity> modelEntities;

    public void addModel(ModelEntity modelEntity) {
        modelEntity.setBrand(this);
        modelEntities.add(modelEntity);
    }

    public  List<ModelEntity> getModelEntities() {
        return List.copyOf(modelEntities);
    }
}
