package by.zemich.advertisementservice.application.usecases;

import by.zemich.advertisementservice.domain.command.CreateCategoryCommand;
import by.zemich.advertisementservice.domain.command.DeleteCategoryByIdCommand;
import by.zemich.advertisementservice.domain.command.UpdateBuIdCategoryCommand;
import by.zemich.advertisementservice.domain.entity.Category;
import by.zemich.advertisementservice.domain.valueobject.CategoryId;

import java.util.Collection;

public interface CategoryCommandUseCase {

    CategoryId handle(CreateCategoryCommand command);
    void handle(DeleteCategoryByIdCommand command);
    Category handle(UpdateBuIdCategoryCommand command );

    Collection<Object> getAll();
}
