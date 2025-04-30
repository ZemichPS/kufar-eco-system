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
    private List<Model> models;

    public void addModel(Model model) {
        model.setBrand(this);
        models.add(model);
    }

    public  List<Model> getModels() {
        return List.copyOf(models);
    }


}
