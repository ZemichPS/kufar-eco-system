package by.zemich.advertisementservice.application.usecases;

import by.zemich.advertisementservice.domain.entity.Category;

public interface CategoryUseCase {
    void persist(Category category);
}
