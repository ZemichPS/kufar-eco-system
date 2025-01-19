package by.zemich.kufar.infrastructure.properties;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "app-set")
public class CategoryParseListProperties {

    @Setter
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
