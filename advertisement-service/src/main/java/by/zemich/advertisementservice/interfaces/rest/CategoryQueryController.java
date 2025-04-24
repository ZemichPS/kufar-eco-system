package by.zemich.advertisementservice.interfaces.rest;

import by.zemich.advertisementservice.application.usecases.CategoryQueryUseCase;
import by.zemich.advertisementservice.domain.query.GetCategoryByIdQuery;
import by.zemich.advertisementservice.domain.dto.CategoryFullDto;
import by.zemich.advertisementservice.domain.valueobject.CategoryId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryQueryController {
    private final CategoryQueryUseCase queryUseCase;

    @GetMapping
    public ResponseEntity<List<CategoryFullDto>> getAllCategories() {
        List<CategoryFullDto> response = queryUseCase.loadAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{categoryUuid}")
    public ResponseEntity<CategoryFullDto> getCategoryById(@PathVariable UUID categoryUuid) {
        CategoryId id = new CategoryId(categoryUuid);
        CategoryFullDto response = queryUseCase.getById(new GetCategoryByIdQuery(id));
        return ResponseEntity.ok(response);
    }

}
