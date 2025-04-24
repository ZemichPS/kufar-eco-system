package by.zemich.advertisementservice.application.usecases;

import by.zemich.advertisementservice.domain.query.GetFullAdvertisementQuery;
import by.zemich.advertisementservice.domain.response.FullAdvertisementDto;

public interface AdvertisementQueryUseCases {

    FullAdvertisementDto load(GetFullAdvertisementQuery query);

}
