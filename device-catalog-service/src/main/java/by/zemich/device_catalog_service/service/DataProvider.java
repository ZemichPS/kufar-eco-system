package by.zemich.device_catalog_service.service;

import by.zemich.device_catalog_service.domen.dto.BrandDto;

import java.util.List;

public interface DataProvider {
    List<BrandDto> getData();
}
