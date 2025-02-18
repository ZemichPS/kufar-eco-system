package by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "category_attributes", schema = "app")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryAttributeEntity {
    @Id
    private UUID uuid;
    private String name;
    @ManyToMany
    private List<CategoryEntity> categories;

    @OneToMany(mappedBy = "categoryAttribute",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<AdvertisementAttributeEntity> advertisementAttributes;
}
