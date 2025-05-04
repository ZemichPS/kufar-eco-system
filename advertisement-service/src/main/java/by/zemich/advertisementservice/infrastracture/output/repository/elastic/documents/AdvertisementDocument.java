package by.zemich.advertisementservice.infrastracture.output.repository.elastic.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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

    @Field(
            type = FieldType.Text,
            analyzer = "russian",
            searchAnalyzer = "russian"
    )
    private String categoryName;

    @Field(
            type = FieldType.Text,
            analyzer = "russian",
            searchAnalyzer = "russian"
    )
    String attributeValue;
    private List<String> attributesValues;

}

