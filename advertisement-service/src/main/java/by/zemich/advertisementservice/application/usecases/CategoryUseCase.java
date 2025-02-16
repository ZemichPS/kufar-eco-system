package by.zemich.advertisementservice.application.usecases;

import by.zemich.advertisementservice.domain.entity.Category;
import by.zemich.advertisementservice.domain.valueobject.Id;

public interface CategoryUseCase {
    Id create(String categoryName);
    void deleteById(Id categoryId);

    Category updateById(Id categoryId, String categoryName);

}
