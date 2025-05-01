package by.zemich.geo_service.service;

import by.zemich.geo_service.domain.dtos.GeoDataDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class KufarDataProvider implements DataProvider {

    private final String GEO_URL = "https://cre-api-v2.kufar.by/yandex-geocoder/static/regions";
    private final RestClient restClient;

    @Override
    public List<GeoDataDto> getData() {

        List<GeoDataResponseDTO> data = restClient.get()
                .uri(GEO_URL)
                .retrieve()
                .body(new ParameterizedTypeReference<List<GeoDataResponseDTO>>() {
                });

        return data.stream()
                .map(response-> GeoDataDto.builder()
                        .id(response.getId())
                        .pid(response.getPid())
                        .name(response.getLabels().ru)
                        .type(response.getType())
                        .tag(response.getTag())
                        .region(response.getRegion())
                        .area(response.getArea())
                        .build())
                .toList();
    }

    @Data
    public static class GeoDataResponseDTO {
        private Integer id;
        private Integer pid;
        private Labels labels;
        private String type;
        private String tag;
        private List<Double> bbox;
        private Integer region;
        private Integer area;

        @Data
        public static class Labels {
            private String ru;
            private String ru_pred;
            private String by;
            private String by_pred;
        }
    }
}

