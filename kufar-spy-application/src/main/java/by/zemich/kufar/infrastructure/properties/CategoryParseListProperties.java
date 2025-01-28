package by.zemich.kufar.infrastructure.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ConfigurationProperties(prefix = "app-set")
@RefreshScope
public class CategoryParseListProperties {

    private List<String> categories = new ArrayList<>();

    public List<String> getCategories() {
        return categories.stream().toList();
    }

    public boolean addCategory(String category) {
        return categories.add(category);
    }

    public boolean removeCategory(String category) {
        return categories.remove(category);
    }
}





