package by.zemich.parser.infrastructure.clients.dto;

import lombok.*;

import java.util.List;

@Data
@EqualsAndHashCode
@ToString
@AllArgsConstructor @NoArgsConstructor @Builder
public class ManufacturerDto {
    private Long id;
    private String name;
    private List<ModelDto> models;

    @Data
    @EqualsAndHashCode
    @ToString
    @AllArgsConstructor @NoArgsConstructor @Builder
    public static class ModelDto {
        private String kufarId;
        private String name;
    }
}
