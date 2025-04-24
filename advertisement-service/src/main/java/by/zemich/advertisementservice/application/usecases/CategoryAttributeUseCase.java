package by.zemich.advertisementservice.application.usecases;

import by.zemich.advertisementservice.domain.command.CreateCategoryAttributeCommand;
import by.zemich.advertisementservice.domain.command.DeleteCategoryAttributeCommand;
import by.zemich.advertisementservice.domain.command.UpdateCategoryAttributeCommand;
import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import by.zemich.advertisementservice.domain.valueobject.CategoryAttributeId;

import java.util.List;

public interface CategoryAttributeUseCase {
    void handle(DeleteCategoryAttributeCommand command);

    List<CategoryAttribute> getAll();

    CategoryAttribute getById(CategoryAttributeId id);

    CategoryAttributeId handle(CreateCategoryAttributeCommand command);

    void handle(UpdateCategoryAttributeCommand command);
}
