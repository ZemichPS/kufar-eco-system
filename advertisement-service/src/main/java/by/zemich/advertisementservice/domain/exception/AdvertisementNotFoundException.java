package by.zemich.advertisementservice.domain.exception;

public class AdvertisementNotFoundException extends EntityNotFoundException{
    public AdvertisementNotFoundException(String categoryAttributeUuid) {
        super("Advertisement attribute UUID " + categoryAttributeUuid + " not found");
    }
}
