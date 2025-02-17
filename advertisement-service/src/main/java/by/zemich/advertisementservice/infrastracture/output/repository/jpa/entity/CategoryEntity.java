package by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "categories", schema = "app")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryEntity {
    @Id
    private UUID uuid;
    private String name;
    @ManyToMany
    @Setter(AccessLevel.NONE)
    private Set<CategoryAttributeEntity> attributes;

    public CategoryEntity(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public boolean addAttribute(CategoryAttributeEntity attribute) {
        attribute.setCategory(this);
        return attributes.add(attribute);
    }
    public boolean removeAttribute(CategoryAttributeEntity attribute) {
        attribute.setCategory(null);
        return attributes.remove(attribute);
    }
}
