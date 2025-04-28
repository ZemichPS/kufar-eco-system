package by.zemich.advertisementservice.domain.query;

import by.zemich.advertisementservice.domain.dto.AdvertisementFilter;
import by.zemich.advertisementservice.domain.valueobject.UserId;
import org.springframework.data.domain.Pageable;

public record GetFilteredPageWithMyAdsQuery(
        AdvertisementFilter filter,
        Pageable pageable
) {

}
