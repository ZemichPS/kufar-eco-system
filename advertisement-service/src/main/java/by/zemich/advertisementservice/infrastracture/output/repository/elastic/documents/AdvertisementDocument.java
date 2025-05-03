package by.zemich.advertisementservice.infrastracture.output.repository.elastic.documents;

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

    @Field(type = FieldType.Keyword, index = false)
    UUID userUuid;

    @Field(
            type = FieldType.Text,
            analyzer = "russian",
            searchAnalyzer = "russian"
    )
    private String categoryName;

    @Field(type = FieldType.Text,
            analyzer = "russian",
            searchAnalyzer = "russian"
    )
    private String condition;

    @Field(type = FieldType.Date, index = false)
    private LocalDateTime publishedAt;

    @Field(type = FieldType.Keyword, index = false)
    private BigDecimal priceInByn;

    @Field(type = FieldType.Keyword, index = false)
    private String comment;

    @Field(type = FieldType.Keyword, index = false)
    private String photoFileName;

    @Field(type = FieldType.Boolean, index = false)
    private Boolean active;

    @Field(type = FieldType.Keyword, index = false)
    private String side;

    @Field(type = FieldType.Nested, searchAnalyzer = "russian", analyzer = "russian")
    private List<NestedAttributeDocument> attributes;
}

