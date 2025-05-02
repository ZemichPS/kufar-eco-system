package by.zemich.advertisementservice.infrastracture.output.repository.elastic.documents;

import by.zemich.advertisementservice.domain.dto.FullAdvertisementDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Document(indexName = "advertisements")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementDocument {
    @Id
    private UUID uuid;

    UUID userUuid;

    @Field(
            type = FieldType.Text,
            analyzer = "russian",
            searchAnalyzer = "russian"
    )
    private String categoryName;
    private String condition;
    private LocalDateTime publishedAt;
    private BigDecimal priceInByn;
    private String comment;
    private String photoFileName;
    private Boolean active;
    private String side;
    private List<NestedAttributeDocument> attributes;

}

