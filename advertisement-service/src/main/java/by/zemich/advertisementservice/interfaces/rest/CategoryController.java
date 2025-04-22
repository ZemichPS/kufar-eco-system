package by.zemich.advertisementservice.interfaces.rest;

import by.zemich.advertisementservice.application.usecases.CategoryAttributeUseCase;
import by.zemich.advertisementservice.application.usecases.CommandCategoryUseCase;
import by.zemich.advertisementservice.domain.entity.Category;
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
    private final CommandCategoryUseCase commandCategoryUseCase;
    private final CategoryAttributeUseCase categoryAttributeUseCase;

    @PostMapping
    public ResponseEntity<URI> create(@RequestBody CategoryRequestDto request) {
        String categoryName = request.getName();
        Id categoryId = commandCategoryUseCase.handle(categoryName);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{categoryUuid}")
                .buildAndExpand(categoryId.uuid())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        List<CategoryResponseDto> response = commandCategoryUseCase.getAll().stream()
                .map(category -> {
                            List<CategoryAttributeResponseDto> attributes = category.getAttributes()
                                    .stream().map(CategoryAttributeMapper::mapToDto).toList();
                            CategoryResponseDto responseDto = CategoryMapper.mapToDto(category);
                            responseDto.setAttributes(attributes);
                            return responseDto;
                        }
                )
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{categoryUuid}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable UUID categoryUuid) {
        Id id = new Id(categoryUuid);
        Category category = commandCategoryUseCase.getById(id);
        CategoryResponseDto response = CategoryMapper.mapToDto(category);
        category.getAttributes().stream()
                .map(CategoryAttributeMapper::mapToDto)
                .forEach(response.getAttributes()::add);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{categoryUuid}")
    public ResponseEntity<CategoryResponseDto> update(@PathVariable UUID categoryUuid, @RequestBody CategoryRequestDto request) {
        String categoryName = request.getName();
        Id categoryId = new Id(categoryUuid);
        Category updatedCategory = commandCategoryUseCase.handle(categoryId);
        CategoryResponseDto response = CategoryMapper.mapToDto(updatedCategory);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{categoryUuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID categoryUuid) {
        Id categoryId = new Id(categoryUuid);
        commandCategoryUseCase.handle(categoryId);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{categoryUuid}/attributes/{attributeUuid}")
    public ResponseEntity<URI> addAttribute(
            @PathVariable UUID categoryUuid,
            @PathVariable UUID attributeUuid
    ) {
        Id categoryId = new Id(categoryUuid);
        Id attributeId = new Id(attributeUuid);
        categoryId = commandCategoryUseCase.addAttribute(categoryId, attributeId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{categoryUuid}")
                .buildAndExpand(categoryId.uuid())
                .toUri();
        return ResponseEntity.created(location).build();
    }


}
