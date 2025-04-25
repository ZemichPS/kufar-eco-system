package by.zemich.advertisementservice.domain.entity;


import by.zemich.advertisementservice.domain.command.CreateAdvertisementCommand;
import by.zemich.advertisementservice.domain.valueobject.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Advertisement {
    private AdvertisementId id;
    private UserId userId;
    private CategoryId categoryId;
    private Condition condition;
    private LocalDateTime publishedAt;
    private LocalDateTime reactivatedAt;
    private Price price;
    private Comment comment;
    private Photo photo;
    private boolean active;
    private List<AdvertisementAttribute> attributes;
    private Side side;

    public Advertisement (CreateAdvertisementCommand command) {
        this.id = command.advertisementId();
        this.userId = command.userId();
        this.categoryId = command.categoryId();
        this.condition = command.condition();
        this.publishedAt = command.publishedAt();
        this.price = command.price();
        this.comment = command.comment();
        this.active = command.active();
        this.attributes = new ArrayList<>();
        this.side = command.side();
    }

    public Advertisement (AdvertisementId advertisementId,
                          UserId userId,
                          CategoryId categoryId,
                          Condition condition,
                          Price price,
                          LocalDateTime publishedAt,
                          Comment comment,
                          boolean active,
                          Photo photo,
                          Side side) {
        this.id = advertisementId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.condition = condition;
        this.publishedAt = publishedAt;
        this.price = price;
        this.comment = comment;
        this.photo = photo;
        this.active = active;
        this.attributes = new ArrayList<>();
        this.side = side;
    }

    public void addAttribute(AdvertisementAttribute attribute) {
        attributes.add(attribute);
    }

    public void changeComment(Comment comment) {
        this.comment = comment;
    }

    public void changeCondition(Condition condition) {
        this.condition = condition;
    }

    public void changePrice(Price price) {
        this.price = price;
    }

    public void changePhoto(Photo photo) {
        this.photo = photo;
    }

    public void activate() {
        this.active = true;
        if (active) reactivatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.active = false;
    }
}
