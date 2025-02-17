package by.zemich.advertisementservice.domain.entity;


import by.zemich.advertisementservice.domain.valueobject.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Advertisement {
    private Id id;
    private Id userId;
    private Category category;
    private Condition condition;
    private LocalDateTime publishedAt;
    @Setter
    private LocalDateTime activatedAt;
    @Setter
    private Price price;
    private Comment comment;
    private Photo photo;
    @Setter
    private boolean active;
    private List<AdvertisementAttribute> attributes;

    public Advertisement(Id id,
                         Id userId,
                         Category category,
                         Condition condition,
                         LocalDateTime publishedAt,
                         LocalDateTime activatedAt,
                         Price price,
                         Comment comment,
                         Photo photo,
                         boolean active
    ) {
        this.id = id;
        this.userId = userId;
        this.category = category;
        this.condition = condition;
        this.publishedAt = publishedAt;
        this.activatedAt = activatedAt;
        this.price = price;
        this.comment = comment;
        this.photo = photo;
        this.active = active;
        attributes = new ArrayList<>();
    }

    public boolean addAttribute(AdvertisementAttribute attribute) {
        return attributes.add(attribute);
    }


}
