package by.zemich.geo_service.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeoDataDto {
    private Integer id;
    private Integer pid;
    private String name;
    private String type;
    private String tag;
    private Integer region;
    private Integer area;
}
