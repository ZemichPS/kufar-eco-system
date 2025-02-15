package by.zemich.advertisementservice.infrastracture.input.rest.mappers;

import by.zemich.advertisementservice.domain.entity.Advertisement;

public class AdvertisementMapper {
    public static Advertisement mapToDomain(){
        return new Advertisement();
    }
}
