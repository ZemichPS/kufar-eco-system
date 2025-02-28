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
    private User user;
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
    private Side side;

    public Advertisement(Id id,
                         User user,
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
        this.user = user;
        this.category = category;
        this.condition = condition;
        this.publishedAt = publishedAt;
        this.activatedAt = activatedAt;
        this.price = price;
        this.comment = comment;
        this.photo = photo;
        this.active = active;
        this.attributes = new ArrayList<>();
    }

    public boolean addAttribute(AdvertisementAttribute attribute) {
        return attributes.add(attribute);
    }

    public void addCategory(Category category) {
        this.category = category;
    }
}
