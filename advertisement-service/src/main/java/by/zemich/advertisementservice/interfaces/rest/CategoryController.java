package by.zemich.advertisementservice.interfaces.rest;

import by.zemich.advertisementservice.application.usecases.CategoryCommandUseCase;
import by.zemich.advertisementservice.application.usecases.CategoryQueryUseCase;
import by.zemich.advertisementservice.domain.command.CreateCategoryCommand;
import by.zemich.advertisementservice.domain.command.DeleteCategoryByIdCommand;
import by.zemich.advertisementservice.domain.command.UpdateBuIdCategoryCommand;
import by.zemich.advertisementservice.domain.entity.Category;
import by.zemich.advertisementservice.domain.query.GetCategoryByIdQuery;
import by.zemich.advertisementservice.domain.response.CategoryFullDto;
import by.zemich.advertisementservice.domain.valueobject.CategoryAttributeId;
import by.zemich.advertisementservice.domain.valueobject.CategoryId;
import by.zemich.advertisementservice.domain.valueobject.Id;
import by.zemich.advertisementservice.interfaces.rest.data.request.CategoryRequestDto;
import by.zemich.advertisementservice.interfaces.rest.data.response.CategoryAttributeResponseDto;
import by.zemich.advertisementservice.interfaces.rest.data.response.CategoryResponseDto;
import by.zemich.advertisementservice.interfaces.rest.mappers.CategoryAttributeMapper;
import by.zemich.advertisementservice.interfaces.rest.mappers.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryCommandUseCase commandCategoryUseCase;
    private final CategoryQueryUseCase queryUseCase;

    @PostMapping
    public ResponseEntity<URI> create(@RequestBody CategoryRequestDto request) {
        CreateCategoryCommand command = new CreateCategoryCommand(
                new CategoryId(UUID.randomUUID()),
                request.getName()
        );
        CategoryId categoryId = commandCategoryUseCase.handle(command);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{categoryUuid}")
                .buildAndExpand(categoryId.uuid())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{categoryUuid}")
    public ResponseEntity<CategoryResponseDto> update(@PathVariable UUID categoryUuid, @RequestBody CategoryRequestDto request) {
        String categoryName = request.getName();
        CategoryId id = new CategoryId(categoryUuid);
        UpdateBuIdCategoryCommand command = new UpdateBuIdCategoryCommand(id, categoryName);
        Category updatedCategory = commandCategoryUseCase.handle(command);
        CategoryResponseDto response = CategoryMapper.mapToDto(updatedCategory);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{categoryUuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID categoryUuid) {
        CategoryId id = new CategoryId(categoryUuid);
        DeleteCategoryByIdCommand command = new DeleteCategoryByIdCommand(id);
        commandCategoryUseCase.handle(command);
        return ResponseEntity.noContent().build();
    }

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


    @PostMapping("/{categoryUuid}/attributes/{attributeUuid}")
    public ResponseEntity<URI> addAttribute(
            @PathVariable UUID categoryUuid,
            @PathVariable UUID attributeUuid
    ) {
        CategoryId categoryId = new CategoryId(categoryUuid);
        CategoryAttributeId attributeId = new CategoryAttributeId(attributeUuid);
        categoryId = commandCategoryUseCase.handle(categoryId, attributeId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{categoryUuid}")
                .buildAndExpand(categoryId.uuid())
                .toUri();
        return ResponseEntity.created(location).build();
    }


}
