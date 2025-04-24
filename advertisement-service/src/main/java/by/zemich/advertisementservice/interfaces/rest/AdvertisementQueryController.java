package by.zemich.advertisementservice.interfaces.rest;

import by.zemich.advertisementservice.application.usecases.AdvertisementQueryUseCases;
import by.zemich.advertisementservice.domain.dto.AdvertisementFilter;
import by.zemich.advertisementservice.domain.query.GetFullAdvertisementQuery;
import by.zemich.advertisementservice.domain.dto.FullAdvertisementDto;
import by.zemich.advertisementservice.domain.valueobject.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/advertisements")
@RequiredArgsConstructor
public class AdvertisementQueryController {

    private final AdvertisementQueryUseCases queryUseCases;

    @GetMapping("/{advertisementUuid}")
    public ResponseEntity<FullAdvertisementDto> getById(@PathVariable UUID advertisementUuid) {
        AdvertisementId id = new AdvertisementId(advertisementUuid);
        FullAdvertisementDto response = queryUseCases.loadPage(new GetFullAdvertisementQuery(id));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PagedModel<FullAdvertisementDto>> findAll(@ModelAttribute AdvertisementFilter filter, Pageable pageable) {
        Page<FullAdvertisementDto> page = queryUseCases.loadPage(filter, pageable);
        return ResponseEntity.ok(new PagedModel<>(page));
    }

}
