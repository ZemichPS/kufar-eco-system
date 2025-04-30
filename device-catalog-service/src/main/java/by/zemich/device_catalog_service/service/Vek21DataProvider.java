package by.zemich.device_catalog_service.service;

import by.zemich.device_catalog_service.domen.dto.BrandDto;
import by.zemich.device_catalog_service.domen.dto.ModelDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(
        prefix = "data-provider",
        name = "resource",
        havingValue = "vek21"
)
@RequiredArgsConstructor
public class Vek21DataProvider implements DataProvider {


    private final RestClient restClient;

    @Override
    public List<BrandDto> getData() {

        String brandRequestDto = "{\"templateId\":85,\"attributes\":[],\"discountTypes\":[],\"deliveryType\":null,\"attributeId\":13485,\"producerIds\":[]}";

        MetaResponseDto responseDto = restClient.post()
                .uri("https://gate.21vek.by/product-listings/api/v1/products/attribute_values")
                .body(brandRequestDto)
                .retrieve()
                .body(MetaResponseDto.class);

        return responseDto.getMeta().getAttributeValues().values().stream()
                .flatMap(List::stream)
                .map(AttributeValueDto::getName)
                .map(name -> {
                    String modelName = name.substring(0, name.indexOf("(")).trim();
                    String brandName = name.substring(name.indexOf("(") + 1, name.indexOf(")")).trim();
                    return new AbstractMap.SimpleEntry<>(brandName, modelName);
                })
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ))
                .entrySet().stream()
                .map(entry -> BrandDto.builder()
                        .name(entry.getKey())
                        .uuid(UUID.randomUUID())
                        .models(entry.getValue().stream()
                                .map(modelName -> ModelDto.builder()
                                        .uuid(UUID.randomUUID())
                                        .name(modelName)
                                        .build())
                                .toList())
                        .build())
                .collect(Collectors.toList());
    }

    @Data
    static class AttributeValueDto {
        private int id;
        private String name;
        private String key;
    }

    @Data
    static class AttributeValuesDto {
        private Map<String, List<AttributeValueDto>> attributeValues;
    }

    @Data
    static class MetaResponseDto {
        private AttributeValuesDto meta;
    }
}
