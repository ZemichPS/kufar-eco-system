package by.zemich.advertisementservice.infrastracture.ouput.repository.jpa.entity;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "advertisement", schema = "app")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdvertisementEntity {
    @Id
    private UUID uuid;
    @ManyToOne
    private CategoryEntity category;
    @ManyToOne
    private UserEntity user;
    private Condition condition;
    private LocalDateTime publishedAt;
    private LocalDateTime activatedAt;
    private BigDecimal priceInByn;
    private String comment;
    private String photoFileName;
    private Boolean active;
    @OneToMany
    private List<AdvertisementAttributeEntity> attributes;

    @Getter
    public enum Condition {
        NEW("новое"),
        USED("б.у."),
        BROKEN("не исправно");
        private String conditionDescription;

        Condition(String conditionDescription) {
        }
    }

}
