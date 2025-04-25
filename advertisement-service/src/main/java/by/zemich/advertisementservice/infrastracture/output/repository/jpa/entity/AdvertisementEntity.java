package by.zemich.advertisementservice.infrastracture.output.repository.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "advertisements", schema = "app")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdvertisementEntity {
    @Id
    private UUID uuid;
    private UUID userUuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_uuid", referencedColumnName = "uuid")
    private CategoryEntity category;

    @Enumerated(EnumType.STRING)
    private Condition condition;
    private LocalDateTime publishedAt;
    private LocalDateTime reactivatedAt;
    private BigDecimal priceInByn;
    private String comment;
    private String photoFileName;
    private Boolean active;
    private Side side;

    @Builder.Default
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "advertisement",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<AdvertisementAttributeEntity> attributes = new ArrayList<>();

    public boolean addAttribute(AdvertisementAttributeEntity attribute) {
        attribute.setAdvertisement(this);
        return attributes.add(attribute);
    }

    @Getter
    public enum Condition {
        NEW,
        USED,
        BROKEN;
    }

    public enum Side {
        BUY,
        SELL;
    }

}
