package by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;


@Entity
@Table(name = "category_attributes", schema = "app")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryAttributeEntity {
    @Id
    private UUID uuid;
    private String name;
    @ManyToOne
    private CategoryEntity category;

}
