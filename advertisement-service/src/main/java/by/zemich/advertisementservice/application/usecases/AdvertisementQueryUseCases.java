package by.zemich.advertisementservice.application.usecases;

import by.zemich.advertisementservice.domain.query.GetFullAdvertisementQuery;
import by.zemich.advertisementservice.domain.response.FullAdvertisementResponse;

public interface AdvertisementQueryUseCases {

    FullAdvertisementResponse load(GetFullAdvertisementQuery query);

}
