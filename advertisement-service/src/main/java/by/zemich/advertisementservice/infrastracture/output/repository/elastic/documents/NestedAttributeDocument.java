package by.zemich.advertisementservice.infrastracture.output.repository.elastic.documents;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
public class NestedAttributeDocument {
    String attributeName;
    @Field(
            type = FieldType.Text,
            analyzer = "russian",
            searchAnalyzer = "russian"
    )
    String attributeValue;
}
