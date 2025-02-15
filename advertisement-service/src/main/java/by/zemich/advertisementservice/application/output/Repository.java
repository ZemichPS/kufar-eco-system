package by.zemich.advertisementservice.application.output;

import by.zemich.advertisementservice.domain.entity.Advertisement;

public interface Repository {
    void save(Advertisement advertisement);
}
