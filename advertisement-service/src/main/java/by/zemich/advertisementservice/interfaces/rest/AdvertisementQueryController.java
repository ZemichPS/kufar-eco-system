package by.zemich.advertisementservice.interfaces.rest;

import by.zemich.advertisementservice.application.usecases.AdvertisementQueryUseCases;
import by.zemich.advertisementservice.domain.dto.AdvertisementFilter;
import by.zemich.advertisementservice.domain.dto.FullAdvertisementDto;
import by.zemich.advertisementservice.domain.query.GetFilteredPageQuery;
import by.zemich.advertisementservice.domain.query.GetFilteredPageWithMyAdsQuery;
import by.zemich.advertisementservice.domain.query.GetFullAdvertisementQuery;
import by.zemich.advertisementservice.domain.valueobject.AdvertisementId;
import by.zemich.advertisementservice.domain.valueobject.UserId;
import by.zemich.advertisementservice.infrastracture.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/my")
    public ResponseEntity<PagedModel<FullAdvertisementDto>> getMyAdvertisements(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @ModelAttribute AdvertisementFilter filter,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        UserId userId = new UserId(userDetails.getUuid());
        filter.setUserId(userId);

        GetFilteredPageWithMyAdsQuery query = new GetFilteredPageWithMyAdsQuery(
                filter,
                pageable
        );
        Page<FullAdvertisementDto> page = queryUseCases.loadMyAdsPage(query);
        return ResponseEntity.ok(new PagedModel<>(page));
    }

    @GetMapping
    public ResponseEntity<PagedModel<FullAdvertisementDto>> findAll(
            @ModelAttribute AdvertisementFilter filter,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        GetFilteredPageQuery query = new GetFilteredPageQuery(filter, pageable);
        Page<FullAdvertisementDto> page = queryUseCases.loadPage(query);
        return ResponseEntity.ok(new PagedModel<>(page));
    }

}
