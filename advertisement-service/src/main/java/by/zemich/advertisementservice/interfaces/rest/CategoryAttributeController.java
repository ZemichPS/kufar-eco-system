package by.zemich.advertisementservice.interfaces.rest;

import by.zemich.advertisementservice.application.usecases.CategoryAttributeUseCase;
import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import by.zemich.advertisementservice.domain.valueobject.Id;
import by.zemich.advertisementservice.interfaces.rest.data.request.CategoryAttributeRequestDto;
import by.zemich.advertisementservice.interfaces.rest.data.response.CategoryAttributeResponseDto;
import by.zemich.advertisementservice.interfaces.rest.mappers.CategoryAttributeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/category_attributes")
@RequiredArgsConstructor
public class CategoryAttributeController {
    private final CategoryAttributeUseCase categoryAttributeUseCase;

    @PostMapping
    public ResponseEntity<URI> create(@RequestBody CategoryAttributeRequestDto request) {
        String attributeName = request.getName();
        Id categoryAttributeId = categoryAttributeUseCase.create(attributeName);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{categoryUuid}")
                .buildAndExpand(categoryAttributeId.uuid())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/{categoryAttributeUuid}")
    public ResponseEntity<CategoryAttributeResponseDto> update(
            @PathVariable UUID categoryAttributeUuid,
            @RequestBody CategoryAttributeRequestDto request
    ) {
        Id categoryAttributeId = new Id(categoryAttributeUuid);
        String attributeName = request.getName();
        CategoryAttribute categoryAttribute = categoryAttributeUseCase.updateNameById(categoryAttributeId, attributeName);
        CategoryAttributeResponseDto requestDto = CategoryAttributeMapper.mapToDto(categoryAttribute);
        return ResponseEntity.ok(requestDto);
    }

    @GetMapping
    public ResponseEntity<List<CategoryAttributeResponseDto>> getAll() {
        List<CategoryAttributeResponseDto> response = categoryAttributeUseCase.getAll().stream().
                map(CategoryAttributeMapper::mapToDto)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{categoryAttributeUuid}")
    public ResponseEntity<CategoryAttributeResponseDto> getById(@PathVariable UUID categoryAttributeUuid) {
        Id categoryAttributeId = new Id(categoryAttributeUuid);
        CategoryAttribute attribute = categoryAttributeUseCase.getById(categoryAttributeId);
        CategoryAttributeResponseDto response = CategoryAttributeMapper.mapToDto(attribute);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{categoryAttributeUuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID categoryAttributeUuid) {
        Id attributeId = new Id(categoryAttributeUuid);
        categoryAttributeUseCase.deleteById(attributeId);
        return ResponseEntity.noContent().build();
    }

}
