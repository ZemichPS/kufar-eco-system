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
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID uuid;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_uuid", referencedColumnName = "uuid")
    private Brand brand;

}
