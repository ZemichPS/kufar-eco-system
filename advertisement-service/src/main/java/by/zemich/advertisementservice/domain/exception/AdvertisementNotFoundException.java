package by.zemich.advertisementservice.domain.exception;

public class AdvertisementNotFoundException extends EntityNotFoundException{
    public AdvertisementNotFoundException(String uuid) {
        super("Advertisement with UUID " + uuid + " not found");
    }
}
