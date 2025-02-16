package by.zemich.advertisementservice.infrastracture.input.rest;

import by.zemich.advertisementservice.application.usecases.AdvertisementUseCase;
import by.zemich.advertisementservice.domain.entity.Advertisement;
import by.zemich.advertisementservice.domain.request.Pagination;
import by.zemich.advertisementservice.domain.valueobject.*;
import by.zemich.advertisementservice.infrastracture.input.rest.data.request.NewAdvertisementDTO;
import by.zemich.advertisementservice.infrastracture.input.rest.data.request.PageRequestDto;
import by.zemich.advertisementservice.infrastracture.input.rest.data.response.AdvertisementDto;
import by.zemich.advertisementservice.infrastracture.input.rest.mappers.AdvertisementMapper;
import by.zemich.advertisementservice.infrastracture.input.rest.mappers.PaginationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/advertisements")
@RequiredArgsConstructor
public class AdvertisementRestController {
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
        return null;
    }

    @GetMapping("/active")
    public ResponseEntity<Page<AdvertisementDto>> getAllActiveAds(@RequestBody PageRequestDto request) {
        Pagination pagination = PaginationMapper.mapToDomain(request);
        List<Advertisement> allActiveAds = advertisementUseCases.getAllActive(pagination);
        List<AdvertisementDto> response = allActiveAds.stream()
                .map(AdvertisementMapper::mapToDto).toList();
        PageRequest pageRequest = PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.by(request.getSortBy())
        );
        PageImpl<AdvertisementDto> pageResponse = new PageImpl<>(response, pageRequest, allActiveAds.size());
        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping
    public ResponseEntity<Page<AdvertisementDto>> getAllAds(@RequestBody PageRequestDto request) {
        Pagination pagination = PaginationMapper.mapToDomain(request);
        List<Advertisement> allActiveAds = advertisementUseCases.getAll(pagination);
        List<AdvertisementDto> response = allActiveAds.stream()
                .map(AdvertisementMapper::mapToDto).toList();
        PageRequest pageRequest = PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.by(request.getSortBy())
        );
        PageImpl<AdvertisementDto> pageResponse = new PageImpl<>(response, pageRequest, allActiveAds.size());
        return ResponseEntity.ok(pageResponse);
    }
}
