package by.zemich.device_catalog_service.domen.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@Table(name = "models", schema = "app")
@NoArgsConstructor
@AllArgsConstructor
public class BrandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    private String name;
    private List<Model> models;
}
