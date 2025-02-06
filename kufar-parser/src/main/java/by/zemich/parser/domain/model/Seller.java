package by.zemich.parser.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Builder
@Table(name = "sellers", schema = "app")
public class Seller {
    @Id
    private String id;
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "seller"
    )
    private List<Advertisement> advertisements = new ArrayList<>();
    private Integer feedbackCount;
    private LocalDateTime firstFeedbackCreatedAt;
    private Float rate;

    public Seller(String id) {
        this.id = id;
    }
}
