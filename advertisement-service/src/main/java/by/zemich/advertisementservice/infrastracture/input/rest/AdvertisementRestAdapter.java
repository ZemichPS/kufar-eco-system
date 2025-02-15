package by.zemich.advertisementservice.infrastracture.input.rest;

import by.zemich.advertisementservice.application.usecases.AdvertisementUseCase;
import by.zemich.advertisementservice.domain.entity.Advertisement;
import by.zemich.advertisementservice.infrastracture.input.rest.data.request.NewAdvertisementDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("api/v1/advertisement")
@RequiredArgsConstructor
public class AdvertisementRestAdapter {
    private final AdvertisementUseCase advertisementUseCases;

    @PostMapping
    public ResponseEntity<URI> createAdvertisement(@RequestBody NewAdvertisementDTO newAdvertisementDTO) {
        return null;
    }
}
