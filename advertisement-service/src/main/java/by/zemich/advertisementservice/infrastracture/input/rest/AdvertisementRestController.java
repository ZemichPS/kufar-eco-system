package by.zemich.advertisementservice.infrastracture.input.rest;

import by.zemich.advertisementservice.application.usecases.AdvertisementUseCase;
import by.zemich.advertisementservice.domain.valueobject.*;
import by.zemich.advertisementservice.infrastracture.input.rest.data.request.NewAdvertisementDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
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
}
