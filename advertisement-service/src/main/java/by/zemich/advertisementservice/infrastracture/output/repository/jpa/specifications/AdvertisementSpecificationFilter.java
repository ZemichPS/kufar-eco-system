package by.zemich.advertisementservice.infrastracture.output.repository.jpa.specifications;

import by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity.AdvertisementEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class AdvertisementSpecificationFilter {
    private String categoryName;
    private Condition condition;
    private LocalDateTime publishedAt;
    private BigDecimal priceFrom;
    private BigDecimal priceTo;
    private Boolean active;
    private Side side;

    enum Condition {
        NEW,
        USED,
        BROKEN;
    }

    enum Side {
        BUY,
        SEll
    }

    public Specification<AdvertisementEntity> buildSpecification() {
        return Specification.where(activeSpecification())
                .and(categorySpecification())
                .and(conditionSpecification())
                .and(sideSpecification())
                .and(priceFromSpecification());
    }

    private Specification<AdvertisementEntity> activeSpecification() {
        return ((root, query, criteriaBuilder) -> active
                ? criteriaBuilder.equal(root.get("active"), active)
                : null);
    }

    private Specification<AdvertisementEntity> categorySpecification() {
        return ((root, query, criteriaBuilder) -> StringUtils.hasText(categoryName)
                ? criteriaBuilder.like(root.get("category.name"), categoryName)
                : null);
    }

    private Specification<AdvertisementEntity> conditionSpecification() {
        return ((root, query, criteriaBuilder) -> StringUtils.hasText(condition.name())
                ? criteriaBuilder.like(root.get("condition"), condition.name())
                : null);
    }

    private Specification<AdvertisementEntity> sideSpecification() {
        return ((root, query, criteriaBuilder) -> StringUtils.hasText(side.name())
                ? criteriaBuilder.equal(root.get("side"), side.name())
                : null);
    }

    private Specification<AdvertisementEntity> priceFromSpecification() {
        return ((root, query, criteriaBuilder) -> StringUtils.hasText(priceFrom.toString()) && StringUtils.hasText(priceTo.toString())
                ? criteriaBuilder.between(root.get("priceInByn"), priceFrom, priceTo)
                : null);
    }


}
