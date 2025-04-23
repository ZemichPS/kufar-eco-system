package by.zemich.advertisementservice.interfaces.rest.mappers;

import by.zemich.advertisementservice.domain.query.Pagination;
import by.zemich.advertisementservice.interfaces.rest.data.request.PageRequestDto;

public class PaginationMapper {
    public static Pagination mapToDomain(PageRequestDto dto){
        return Pagination.builder()
                .asc(dto.isAsc())
                .page(dto.getPage())
                .size(dto.getSize())
                .sortBy(dto.getSortBy())
                .build();
    }
}
