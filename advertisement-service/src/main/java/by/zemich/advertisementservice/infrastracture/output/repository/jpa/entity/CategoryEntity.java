package by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

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
    @ManyToMany(
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "category_catattributes",
            joinColumns = @JoinColumn(name = "category_uuid"),
            inverseJoinColumns = @JoinColumn(name = "category_attribute_uuid")
    )
    private Set<CategoryAttributeEntity> attributes;

    public CategoryEntity(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public boolean addAttribute(CategoryAttributeEntity attribute) {
        return attributes.add(attribute);
    }

    public boolean removeAttribute(CategoryAttributeEntity attribute) {
        return attributes.remove(attribute);
    }
}
