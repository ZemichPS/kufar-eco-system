package by.zemich.advertisementservice.application.usecases;

import by.zemich.advertisementservice.domain.valueobject.Id;

public interface CategoryUseCase {
    Id create(String categoryName);
    void delete(Id categoryId);

}
