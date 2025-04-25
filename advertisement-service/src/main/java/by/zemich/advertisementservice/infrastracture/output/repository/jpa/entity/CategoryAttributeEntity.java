package by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;


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

    @Builder.Default
    @ManyToMany
    private Set<CategoryEntity> categories = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "categoryAttribute",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<AdvertisementAttributeEntity> advertisementAttributes = new ArrayList<>();
}
