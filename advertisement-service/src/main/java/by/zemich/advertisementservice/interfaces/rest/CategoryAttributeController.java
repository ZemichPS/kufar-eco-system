package by.zemich.advertisementservice.interfaces.rest;

import by.zemich.advertisementservice.application.usecases.CategoryAttributeUseCase;
import by.zemich.advertisementservice.domain.command.CreateCategoryAttributeCommand;
import by.zemich.advertisementservice.domain.command.DeleteCategoryAttributeCommand;
import by.zemich.advertisementservice.domain.command.UpdateCategoryAttributeCommand;
import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
import by.zemich.advertisementservice.domain.valueobject.CategoryAttributeId;
import by.zemich.advertisementservice.domain.valueobject.CategoryId;
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
        CreateCategoryAttributeCommand command = new CreateCategoryAttributeCommand(
                new CategoryId(request.getCategoryId()),
                new CategoryAttributeId(UUID.randomUUID()),
                request.getName()
        );
        CategoryAttributeId id = categoryAttributeUseCase.handle(command);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{categoryUuid}")
                .buildAndExpand(id.uuid())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/{categoryAttributeUuid}")
    public ResponseEntity<Void> update(
            @PathVariable UUID categoryAttributeUuid,
            @RequestBody CategoryAttributeRequestDto request
    ) {
        UpdateCategoryAttributeCommand command = new UpdateCategoryAttributeCommand(
                new CategoryAttributeId(categoryAttributeUuid),
                request.getName()
        );
        categoryAttributeUseCase.handle(command);
        return ResponseEntity.noContent().build();
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
        categoryAttributeUseCase.handle(new DeleteCategoryAttributeCommand(
                new CategoryAttributeId(categoryAttributeUuid)
        ));
        return ResponseEntity.noContent().build();
    }

}
