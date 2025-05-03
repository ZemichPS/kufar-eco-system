package by.zemich.advertisementservice.infrastracture.output.repository.elastic.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NestedAttributeDocument {

    @Field(type = FieldType.Keyword, index = false)
    String attributeName;

    @Field(
            type = FieldType.Text,
            analyzer = "russian",
            searchAnalyzer = "russian"
    )
    String attributeValue;
}
