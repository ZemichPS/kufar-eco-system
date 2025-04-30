package by.zemich.device_catalog_service.service;

import by.zemich.device_catalog_service.domen.dto.BrandDto;
import by.zemich.device_catalog_service.domen.dto.ModelDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(
        prefix = "data-provider",
        name = "resource",
        havingValue = "kufar"
)
public class KufarProvider implements DataProvider {

    private final RestClient restClient;
    private final String FILTER_URL = "https://api.kufar.by/taxonomy-proxy/v1/dispatch?routing=web_generalist&parent=17000&application=ad_listing&platform=web";

    @Override
    public List<BrandDto> getData() {

        FilterDto filterDto = restClient.get()
                .uri(FILTER_URL)
                .retrieve()
                .body(FilterDto.class);

        List<FilterDto.RuleWrapper> ruleWrappers = filterDto.getMetadata().getParameters().getRules().stream()
                .filter(ruleWrapper -> ruleWrapper.getRule().getCategory() != null)
                .filter(ruleWrapper -> ruleWrapper.getRule().getCategory().equalsIgnoreCase("17010"))
                .toList();

        List<FilterDto.Ref> phonesBrand = filterDto.getMetadata().getParameters().getRefs().values().stream()
                .filter(ref1 -> ref1.getName().equalsIgnoreCase("phones_brand"))
                .toList();

        List<FilterDto.Ref> phonesModels = filterDto.getMetadata().getParameters().getRefs().values().stream()
                .filter(ref1 -> ref1.getName().equalsIgnoreCase("phones_model"))
                .toList();

        return ruleWrappers.stream()
                .map(
                        ruleWrapper -> {
                            String brandId = ruleWrapper.getRule().getPhonesBrand();
                            String brandName = phonesBrand.stream().flatMap(ref -> ref.getValues().stream())
                                    .filter(value -> value.getValue().equalsIgnoreCase(brandId))
                                    .map(value -> value.getLabels().get("ru"))
                                    .findFirst().orElse("");

                            List<ModelDto> modelList = ruleWrapper.getRefs().stream()
                                    .flatMap(modelRefId -> phonesModels.stream().filter(ref -> ref.getVariationId().equals(modelRefId)))
                                    .flatMap(ref -> ref.getValues().stream())
                                    .map(value -> new ModelDto(
                                            UUID.randomUUID(),
                                            value.getLabels().get("ru")
                                    ))
                                    .toList();


                            return BrandDto.builder()
                                    .uuid(UUID.randomUUID())
                                    .name(brandName)
                                    .models(modelList)
                                    .build();
                        }
                )
                .toList();
    }

    @Data
    static class FilterDto {
        private Metadata metadata;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Metadata {
            private Parameters parameters;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Parameters {
            private String dispatchType;
            private String deduplicationKey;
            private Map<String, Ref> refs;
            private List<RuleWrapper> rules;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Ref {
            private String variationId;
            private String name;
            private String urlName;
            private boolean required;
            private String type;
            private String meta;
            private boolean multi;
            private Hint hint;
            private List<Action> actions;
            private Object grouping;
            private Labels labels;
            private List<Value> values;
            private String externalValuesUrl;
            private Range range;
            private int minTaxonomyVersion;
            private String imageUrl;
            @JsonProperty("is_type")
            private boolean isType;
        }

        @Data
        public static class Hint {
            private String ru;
            private String by;
        }

        @Data
        public static class Action {
            private int actionId;
            private Object args;
            private Object dependsOn;
            private String name;
            private String type;
        }

        @Data
        public static class Labels {
            private Map<String, String> name;
            private Map<String, String> placeholder;
        }

        @Data
        public static class Value {
            private String value;
            private Map<String, String> labels;
            private String hint;
            private String imageUrl;
        }

        @Data
        public static class Range {
            private String defaultLower;
            private String defaultUpper;
            private String lower;
            private String upper;
            private String step;
        }

        @Data
        public static class RuleWrapper {
            private Rule rule;
            private List<String> refs;
        }

        @Data
        public static class Rule {
            private String region;
            private String category;
            private String phonesBrand;
            private String companyAd;
            private String phabletSmartWatchesType;
        }
    }
}
