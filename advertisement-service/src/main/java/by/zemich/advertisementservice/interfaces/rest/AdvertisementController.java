package by.zemich.advertisementservice.interfaces.rest;

import by.zemich.advertisementservice.application.usecases.AdvertisementUseCase;
import by.zemich.advertisementservice.domain.entity.Advertisement;
import by.zemich.advertisementservice.domain.entity.User;
import by.zemich.advertisementservice.domain.request.Pagination;
import by.zemich.advertisementservice.domain.valueobject.*;
import by.zemich.advertisementservice.interfaces.rest.data.request.AdvertisementRequestDTO;
import by.zemich.advertisementservice.interfaces.rest.data.request.NewAdvertisementDTO;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/advertisements")
@RequiredArgsConstructor
public class AdvertisementController {
    private final AdvertisementUseCase advertisementUseCases;

    @PostMapping
    public ResponseEntity<URI> createAdvertisement(@RequestBody NewAdvertisementDTO dto) {
        Map<UUID, String> attributes = dto.getAttributes().stream()
                .collect(
                        Collectors.toMap(NewAdvertisementDTO.AdvertisementAttribute::getCategoryAttributeId, NewAdvertisementDTO.AdvertisementAttribute::getValue)
                );
        Id advertisementId = advertisementUseCases.create(
                new Id(dto.getUserId()),
                new Id(dto.getCategoryId()),
                Condition.valueOf(dto.getCondition().name()),
                new Price(dto.getPriceInByn()),
                new Comment(dto.getComment()),
                new Photo(dto.getPhoto()),
                attributes
        );
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{advertisementUuid}")
                .buildAndExpand(advertisementId.uuid())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<AdvertisementResponseDto> updateAdvertisement(@RequestBody AdvertisementRequestDTO dto) {
        Map<UUID, String> attributes = dto.getAttributes().stream()
                .collect(
                        Collectors.toMap(NewAdvertisementDTO.AdvertisementAttribute::getCategoryAttributeId, NewAdvertisementDTO.AdvertisementAttribute::getValue)
                );
        Advertisement advertisement = advertisementUseCases.update(
                new User(new Id(dto.getId())),
                new Id(dto.getCategoryId()),
                Condition.valueOf(dto.getCondition().name()),
                new Price(dto.getPriceInByn()),
                new Comment(dto.getComment()),
                new Photo(dto.getPhoto()),
                attributes
        );
        AdvertisementResponseDto responseDto = AdvertisementMapper.mapToDto(advertisement);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping
    public ResponseEntity<AdvertisementResponseDto> updateAdvertisement(@RequestBody UpdateAdvertisementPriceRequestDTO request) {
        Id advertisementId = new Id(request.getAdvertisementId());
        Price price = new Price(request.getPriceInByn());
        Advertisement advertisement = advertisementUseCases.updatePriceById(advertisementId, price);
        AdvertisementResponseDto responseDto = AdvertisementMapper.mapToDto(advertisement);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{advertisementUuid}")
    public ResponseEntity<AdvertisementResponseDto> getById(@PathVariable UUID advertisementUuid) {
        Id advertisementId = new Id(advertisementUuid);
        Advertisement advertisement = advertisementUseCases.getById(advertisementId);
        AdvertisementResponseDto response = AdvertisementMapper.mapToDto(advertisement);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<AdvertisementResponseDto>> getAll(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy,
            @RequestParam boolean asc,
            @RequestParam boolean onlyActive
    ) {
        Pagination pagination = Pagination.builder()
                .page(page)
                .sortBy(sortBy)
                .asc(asc)
                .onlyActive(onlyActive)
                .size(size).build();
        List<Advertisement> allActiveAds = advertisementUseCases.getAll(pagination);
        List<AdvertisementResponseDto> response = allActiveAds.stream()
                .map(AdvertisementMapper::mapToDto).toList();
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.by(sortBy)
        );
        PageImpl<AdvertisementResponseDto> pageResponse = new PageImpl<>(response, pageRequest, allActiveAds.size());
        return ResponseEntity.ok(pageResponse);
    }

}
