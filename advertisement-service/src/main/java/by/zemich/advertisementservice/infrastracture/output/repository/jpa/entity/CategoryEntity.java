package by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "categories", schema = "app")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString(exclude = {"attributes", "advertisements"})
public class CategoryEntity {
    @Id
    private UUID uuid;
    private String name;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "category"
    )
    private List<AdvertisementEntity> advertisements;

    @Setter(AccessLevel.NONE)
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "category",
            orphanRemoval = true
    )
    @Builder.Default
    private List<CategoryAttributeEntity> attributes = new ArrayList<>();

    public boolean addAttribute(CategoryAttributeEntity attribute) {
        attribute.setCategory(this);
        return attributes.add(attribute);
    }

    public boolean removeAttribute(CategoryAttributeEntity attribute) {
        attribute.setCategory(null);
        return attributes.remove(attribute);
    }
}
