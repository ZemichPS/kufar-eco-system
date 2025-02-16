package by.zemich.advertisementservice.application.ports.output;

import by.zemich.advertisementservice.domain.entity.Advertisement;
import by.zemich.advertisementservice.domain.entity.Category;

public interface CategoryEventOutputPort {
    void publishCategoryCreated(Category category);

}
