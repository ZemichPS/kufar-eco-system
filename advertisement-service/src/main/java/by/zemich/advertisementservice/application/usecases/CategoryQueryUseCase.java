package by.zemich.advertisementservice.application.usecases;

import by.zemich.advertisementservice.domain.query.GetCategoryByIdQuery;
import by.zemich.advertisementservice.domain.response.CategoryFullDto;

import java.util.List;

public interface CategoryQueryUseCase {

   List<CategoryFullDto> loadAll();
   CategoryFullDto getById(GetCategoryByIdQuery query);

}
