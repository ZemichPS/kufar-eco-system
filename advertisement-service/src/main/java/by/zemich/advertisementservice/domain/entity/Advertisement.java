package by.zemich.advertisementservice.domain.entity;


import by.zemich.advertisementservice.domain.valueobject.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Advertisement {
    private Id id;
    private User user;
    private Category category;
    private Condition condition;
    private LocalDateTime publishedAt;
    private Price price;
    private Comment comment;
    private Photo photo;
    private List<AdvertisementAttribute> attributes;
}
