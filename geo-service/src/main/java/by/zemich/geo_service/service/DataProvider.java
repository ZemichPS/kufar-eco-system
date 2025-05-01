package by.zemich.geo_service.service;


import by.zemich.geo_service.domain.dtos.GeoDataDto;

import java.util.List;

public interface DataProvider {
    List<GeoDataDto> getData();
}
