package by.zemich.parser.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "advertisements", schema = "app")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"parameters", "seller"})
@ToString(exclude = {"parameters", "seller"})
public class Advertisement {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;
    private Long adId;
    private String link;
    private String category;
    private boolean companyAd;
    private String currency;
    private LocalDateTime publishedAt;
    private String subject;
    private String type;
    private BigDecimal priceInByn;
    private BigDecimal priceInUsd;
    private String details;
    private boolean fullyFunctional;
    private String images;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    private Seller seller;
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<Parameter> parameters = new ArrayList<>();

    public Advertisement addParameter(Parameter parameter) {
        parameters.add(parameter);
        return this;
    }

    public Advertisement setPriceInByn(BigDecimal priceInByn) {
        this.priceInByn = priceInByn;
        return this;
    }

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

    @JsonIgnore
    public Optional<String> getBrand() {
        return this.parameters.stream()
                .filter(param -> "phones_brand".equals(param.identity))
                .map(param -> param.value)
                .findFirst();
    }

    @JsonIgnore
    public Optional<String> getModel() {
        return this.parameters.stream()
                .filter(param -> "phones_model".equals(param.identity))
                .map(param -> param.value)
                .findFirst();
    }

    @JsonIgnore
    public Optional<String> getParameterValueByParameterName(String parameterName) {
        return this.parameters.stream()
                .filter(param -> parameterName.equals(param.identity))
                .map(param -> param.value)
                .findFirst();
    }

    @JsonIgnore
    public Optional<Parameter> getParameterByIdentity(String identity) {
        return this.parameters.stream()
                .filter(param -> identity.equals(param.identity))
                .findFirst();
    }

    @JsonIgnore
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

    @JsonIgnore
    public String getCondition() {
        return this.parameters.stream()
                .filter(param -> "condition".equals(param.identity))
                .map(param -> param.value)
                .findFirst()
                .orElse("");
    }

    @JsonIgnore
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
