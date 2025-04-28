package by.zemich.advertisementservice.interfaces.rest;

import by.zemich.advertisementservice.application.usecases.AdvertisementCommandUseCases;
import by.zemich.advertisementservice.domain.command.*;
import by.zemich.advertisementservice.domain.valueobject.*;
import by.zemich.advertisementservice.interfaces.rest.data.request.AdvertisementRequestDTO;
import by.zemich.advertisementservice.interfaces.rest.data.request.AttributesDto;
import by.zemich.advertisementservice.interfaces.rest.data.request.NewAdvertisementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/advertisements")
@RequiredArgsConstructor
public class AdvertisementCommandController {
    private final AdvertisementCommandUseCases commandUseCases;

    @PostMapping
    public ResponseEntity<URI> createAdvertisement(@RequestBody NewAdvertisementDto request) {
        CreateAdvertisementCommand command = new CreateAdvertisementCommand(
                new AdvertisementId(UUID.randomUUID()),
                new UserId(request.getUserId()),
                new CategoryId(request.getCategoryId()),
                Condition.valueOf(request.getCondition().name()),
                new Price(request.getPriceInByn()),
                LocalDateTime.now(),
                new Comment(request.getComment()),
                true,
                Side.valueOf(request.getSide().name())
        );

        AdvertisementId id = commandUseCases.handle(command);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{advertisementUuid}")
                .buildAndExpand(id.uuid())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{advertisementUuid}")
    public ResponseEntity<Void> updateAdvertisement(
            @PathVariable UUID advertisementUuid,
            @RequestBody AdvertisementRequestDTO request
    ) {
        UpdateAdvertisementCommand command = new UpdateAdvertisementCommand(
                new AdvertisementId(advertisementUuid),
                Condition.valueOf(request.getCondition()),
                new Price(request.getPrice()),
                new Comment(request.getComment())
        );
        commandUseCases.handle(command);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{advertisementUuid}")
    public ResponseEntity<Void> addAttributes(@PathVariable UUID advertisementUuid, @RequestBody AttributesDto request) {
        Map<CategoryAttributeId, String> attributes = request.getAttributes().entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> new CategoryAttributeId(entry.getKey()),
                        Map.Entry::getValue
                ));
        AddAttributesCommand command = new AddAttributesCommand(new AdvertisementId(advertisementUuid), attributes);
        commandUseCases.handle(command);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{advertisementUuid}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID advertisementUuid) {
        AdvertisementId advertisementId = new AdvertisementId(advertisementUuid);
        DeleteAdvertisementCommand command = new DeleteAdvertisementCommand(advertisementId);
        commandUseCases.handle(command);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/deactivation/{advertisementUuid}")
    public ResponseEntity<Void> deactivateById(@PathVariable UUID advertisementUuid) {
        AdvertisementId advertisementId = new AdvertisementId(advertisementUuid);
        DeactivateAdvertisementCommand command = new DeactivateAdvertisementCommand(advertisementId);
        commandUseCases.handle(command);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/activation/{advertisementUuid}")
    public ResponseEntity<Void> activateById(@PathVariable UUID advertisementUuid) {
        AdvertisementId advertisementId = new AdvertisementId(advertisementUuid);
        ActivateAdvertisementCommand command = new ActivateAdvertisementCommand(advertisementId);
        commandUseCases.handle(command);
        return ResponseEntity.ok().build();
    }

}
