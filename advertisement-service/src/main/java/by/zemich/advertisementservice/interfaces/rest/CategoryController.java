package by.zemich.advertisementservice.interfaces.rest;

import by.zemich.advertisementservice.application.usecases.CategoryAttributeUseCase;
import by.zemich.advertisementservice.application.usecases.CategoryUseCase;
import by.zemich.advertisementservice.domain.entity.Category;
import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import by.zemich.advertisementservice.domain.valueobject.Id;
import by.zemich.advertisementservice.interfaces.rest.data.request.CategoryRequestDto;
import by.zemich.advertisementservice.infrastracture.input.rest.data.response.*;
import by.zemich.advertisementservice.interfaces.rest.data.response.AttributeDtoRequest;
import by.zemich.advertisementservice.interfaces.rest.data.response.CategoryAttributeResponseDto;
import by.zemich.advertisementservice.interfaces.rest.data.response.CategoryResponseDto;
import by.zemich.advertisementservice.interfaces.rest.mappers.CategoryAttributeMapper;
import by.zemich.advertisementservice.interfaces.rest.mappers.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryUseCase categoryUseCase;
    private final CategoryAttributeUseCase categoryAttributeUseCase;

    @PostMapping
    public ResponseEntity<URI> create(@RequestBody CategoryRequestDto request) {
        String categoryName = request.getName();
        Id categoryId = categoryUseCase.create(categoryName);
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
        Id categoryId = new Id(categoryUuid);
        Category updatedCategory = categoryUseCase.updateById(categoryId, categoryName);
        CategoryResponseDto response = CategoryMapper.toDto(updatedCategory);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{categoryUuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID categoryUuid) {
        Id categoryId = new Id(categoryUuid);
        categoryUseCase.deleteById(categoryId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{categoryUuid}/attributes")
    public ResponseEntity<URI> addAttribute(@PathVariable UUID categoryUuid, @RequestBody AttributeDtoRequest request) {
        Id categoryId = new Id(categoryUuid);
        Id attributeId = categoryAttributeUseCase.create(
                categoryId,
                request.getName()
        );
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{attributeUuid}")
                .buildAndExpand(attributeId.uuid())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/attributes/{attributeUuid}")
    public ResponseEntity<Void> deleteAttribute(@PathVariable UUID attributeUuid) {
        categoryAttributeUseCase.deleteById(new Id(attributeUuid));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/attributes/{attributeUuid}")
    public ResponseEntity<CategoryAttributeResponseDto> updateAttribute(@PathVariable UUID attributeUuid, @RequestBody AttributeDtoRequest request) {
        Id attributeId = new Id(attributeUuid);
        String attributeName = request.getName();
        CategoryAttribute updatedAttribute = categoryAttributeUseCase.updateById(attributeId, attributeName);
        CategoryAttributeResponseDto response = CategoryAttributeMapper.toDto(updatedAttribute);
        return ResponseEntity.ok(response);
    }


}
