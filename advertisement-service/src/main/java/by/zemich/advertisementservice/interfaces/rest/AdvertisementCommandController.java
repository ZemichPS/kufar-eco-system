package by.zemich.advertisementservice.interfaces.rest;

import by.zemich.advertisementservice.application.usecases.AdvertisementCommandUseCases;
import by.zemich.advertisementservice.domain.command.CreateAdvertisementCommand;
import by.zemich.advertisementservice.domain.command.DeactivateAdvertisementCommand;
import by.zemich.advertisementservice.domain.command.DeleteAdvertisementCommand;
import by.zemich.advertisementservice.domain.command.UpdateAdvertisementCommand;
import by.zemich.advertisementservice.domain.entity.Advertisement;
import by.zemich.advertisementservice.domain.query.Pagination;
import by.zemich.advertisementservice.domain.valueobject.*;
import by.zemich.advertisementservice.interfaces.rest.data.request.AdvertisementRequestDTO;
import by.zemich.advertisementservice.interfaces.rest.data.request.NewAdvertisementDto;
import by.zemich.advertisementservice.interfaces.rest.data.request.UpdateAdvertisementPriceRequestDTO;
import by.zemich.advertisementservice.interfaces.rest.data.response.AdvertisementResponseDto;
import by.zemich.advertisementservice.interfaces.rest.mappers.AdvertisementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

    @PutMapping
    public ResponseEntity<URI> updateAdvertisement(@RequestBody AdvertisementRequestDTO request) {
        UpdateAdvertisementCommand command = new UpdateAdvertisementCommand(
                new AdvertisementId(request.getAdvertisementId()),
                Condition.valueOf(request.getCondition().name()),
                new Price(request.getPrice()),
                new Comment(request.getComment())
        );
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

    @PatchMapping("/{advertisementUuid}")
    public ResponseEntity<Void> deactivateById(@PathVariable UUID advertisementUuid) {
        AdvertisementId advertisementId = new AdvertisementId(advertisementUuid);
        DeactivateAdvertisementCommand command = new DeactivateAdvertisementCommand(advertisementId);
        commandUseCases.handle(command);
        return ResponseEntity.ok().build();
    }

}
