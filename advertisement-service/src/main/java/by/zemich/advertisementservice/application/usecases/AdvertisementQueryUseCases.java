package by.zemich.advertisementservice.application.usecases;

import by.zemich.advertisementservice.domain.dto.FullAdvertisementDto;
import by.zemich.advertisementservice.domain.query.GetFilteredPageQuery;
import by.zemich.advertisementservice.domain.query.GetFilteredPageWithMyAdsQuery;
import by.zemich.advertisementservice.domain.query.GetFullAdvertisementQuery;
import org.springframework.data.domain.Page;


public interface AdvertisementQueryUseCases {

    FullAdvertisementDto loadPage(GetFullAdvertisementQuery query);

    Page<FullAdvertisementDto> loadPage(GetFilteredPageQuery query);

    Page<FullAdvertisementDto> loadMyAdsPage(GetFilteredPageWithMyAdsQuery query);

    FullAdvertisementDto search(String query);


}
