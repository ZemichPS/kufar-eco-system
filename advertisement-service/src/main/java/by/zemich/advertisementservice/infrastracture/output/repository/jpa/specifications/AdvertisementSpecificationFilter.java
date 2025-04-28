package by.zemich.advertisementservice.infrastracture.output.repository.jpa.specifications;

import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.AdvertisementEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
public class AdvertisementSpecificationFilter {
    private String categoryName;
    private Condition condition;
    private LocalDateTime publishedAt;
    private BigDecimal priceFrom;
    private BigDecimal priceTo;
    private Boolean active;
    private Side side;
    private UUID userId;

    public enum Condition {
        NEW,
        USED,
        BROKEN;
    }

    public enum Side {
        BUY,
        SELL
    }

    public Specification<AdvertisementEntity> buildSpecification() {
        return Specification.where(activeSpecification())
                .and(categorySpecification())
                .and(conditionSpecification())
                .and(sideSpecification())
                .and(userSpecification())
                .and(priceFromSpecification());
    }

    private Specification<AdvertisementEntity> activeSpecification() {
        return ((root, query, criteriaBuilder) -> active != null
                ? criteriaBuilder.equal(root.get("active"), active)
                : null);
    }

    private Specification<AdvertisementEntity> userSpecification() {
        return ((root, query, criteriaBuilder) -> userId != null
                ? criteriaBuilder.equal(root.get("userUuid"), userId)
                : null);
    }

    private Specification<AdvertisementEntity> categorySpecification() {
        return ((root, query, criteriaBuilder) -> StringUtils.hasText(categoryName)
                ? criteriaBuilder.equal(root.get("category").get("name"), categoryName)
                : null);
    }

    private Specification<AdvertisementEntity> conditionSpecification() {
        return ((root, query, criteriaBuilder) -> condition != null
                ? criteriaBuilder.like(root.get("condition"), condition.name())
                : null);
    }

    private Specification<AdvertisementEntity> sideSpecification() {
        return ((root, query, criteriaBuilder) -> side != null
                ? criteriaBuilder.equal(root.get("side"), side.name())
                : null);
    }

    private Specification<AdvertisementEntity> priceFromSpecification() {
        return (root, query, criteriaBuilder) -> {
            if (priceFrom != null && priceTo != null) {
                return criteriaBuilder.between(root.get("priceInByn"), priceFrom, priceTo);
            } else if (priceFrom != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("priceInByn"), priceFrom);
            } else if (priceTo != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("priceInByn"), priceTo);
            } else {
                return null;
            }
        };
    }


}
