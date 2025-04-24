package by.zemich.advertisementservice.application.ports.input;

import by.zemich.advertisementservice.application.ports.output.CategoryPersistenceOutputPort;
import by.zemich.advertisementservice.application.usecases.CategoryQueryUseCase;
import by.zemich.advertisementservice.domain.exception.CategoryNotFoundException;
import by.zemich.advertisementservice.domain.query.GetCategoryByIdQuery;
import by.zemich.advertisementservice.domain.response.CategoryFullDto;
import by.zemich.advertisementservice.domain.utils.CategoryMapper;

import java.util.List;

public class CategoryQueryInputPort implements CategoryQueryUseCase {

    private final CategoryPersistenceOutputPort categoryPersistenceOutputPort;


    public CategoryQueryInputPort(CategoryPersistenceOutputPort categoryPersistenceOutputPort) {
        this.categoryPersistenceOutputPort = categoryPersistenceOutputPort;
    }

    @Override
    public List<CategoryFullDto> loadAll() {
        return categoryPersistenceOutputPort.getAll().stream()
                .map(CategoryMapper::map)
                .toList();

    }

    @Override
    public CategoryFullDto getById(GetCategoryByIdQuery query) {
        return categoryPersistenceOutputPort.getById(query.categoryId())
                .map(CategoryMapper::map)
                .orElseThrow(() -> new CategoryNotFoundException(query.categoryId().uuid().toString()));
    }
}
