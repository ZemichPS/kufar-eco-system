package by.zemich.advertisementservice.domain.query;

import by.zemich.advertisementservice.domain.dto.AdvertisementFilter;
import org.springframework.data.domain.Pageable;

public record GetFilteredPageQuery(AdvertisementFilter filter, Pageable pageable) {

}
