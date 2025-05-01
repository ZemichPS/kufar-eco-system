package by.zemich.geo_service.domain.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "geos", schema = "app")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class GeoDataEntity {
    @Id
    private Integer id;
    private Integer pid;
    private String name;
    private String type;
    private String tag;
    private Integer region;
    private Integer area;
}
