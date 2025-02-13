package by.zemich.advertisementservice.domain.entity;


import by.zemich.advertisementservice.domain.valueobject.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.concurrent.locks.Condition;

@Getter
public class Advertisement {
    private Id id;
    private User user;
    private Category category;
    private LocalDateTime publishedAt;

    private Condition condition;
    private Price price;
    private Comment comment;
    private Photo photo;
}
