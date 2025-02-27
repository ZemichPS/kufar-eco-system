package by.zemich.telegrambotservice.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KufarAdvertisement {
    private UUID id;
    private Long kufarId;
    private String link;
    private String category;
    private String parentCategory;
    private boolean companyAd;
    private LocalDateTime publishedAt;
    private String subject;
    private String type;
    private BigDecimal priceInByn;
    private BigDecimal priceInUsd;
    private BigDecimal commerceMarketPrice;
    private BigDecimal nonCommerceMarketPrice;
    private String details;
    private boolean fullyFunctional;
    private String images;
    private String sellerId;
    private List<Parameter> parameters = new ArrayList<>();


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Parameter {
        public Parameter(String identity, String value) {
            this.identity = identity;
            this.value = value;
        }

        private String identity;
        private String value;
        private String label;
    }

    public Optional<String> getBrand() {
        return this.parameters.stream()
                .filter(param -> "phones_brand".equals(param.identity))
                .map(param -> param.value)
                .findFirst();
    }

    public Optional<String> getModel() {
        return this.parameters.stream()
                .filter(param -> "phones_model".equals(param.identity))
                .map(param -> param.value)
                .findFirst();
    }

    public Optional<BigDecimal> getNonCommerceMarketPrice() {
        return Optional.ofNullable(this.nonCommerceMarketPrice);
    }

    public Optional<BigDecimal> getCommerceMarketPrice() {
        return Optional.ofNullable(this.commerceMarketPrice);
    }

    public Optional<String> getParameterValueByParameterName(String parameterName) {
        return this.parameters.stream()
                .filter(param -> parameterName.equals(param.identity))
                .map(param -> param.value)
                .findFirst();
    }

    public Optional<Parameter> getParameterByIdentity(String identity) {
        return this.parameters.stream()
                .filter(param -> identity.equals(param.identity))
                .findFirst();
    }

    public String getFullAddress() {
        String region = this.parameters.stream()
                .filter(param -> "region".equals(param.identity))
                .map(param -> param.value)
                .findFirst()
                .orElse("");

        String area = this.parameters.stream()
                .filter(param -> "area".equals(param.identity))
                .map(param -> param.value)
                .findFirst()
                .orElse("");

        return area + ", " + region;
    }

    public String getCondition() {
        return this.parameters.stream()
                .filter(param -> "condition".equals(param.identity))
                .map(param -> param.value)
                .findFirst()
                .orElse("");
    }

    public Optional<String> getPhotoLink() {
        if (images.isEmpty()) return Optional.empty();

        String imageFilePath;
        if (images.contains(";")) {
            imageFilePath = this.images.split(";")[0];
        } else imageFilePath = images;

        String url = "https://rms.kufar.by/v1/gallery/{filename.jpg}".replace("{filename.jpg}", imageFilePath);
        return Optional.of(url);
    }
}
