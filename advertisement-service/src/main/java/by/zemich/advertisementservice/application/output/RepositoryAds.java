package by.zemich.advertisementservice.application.output;

import by.zemich.advertisementservice.domain.entity.Advertisement;

public interface RepositoryAds {
    void save(Advertisement advertisement);
}
