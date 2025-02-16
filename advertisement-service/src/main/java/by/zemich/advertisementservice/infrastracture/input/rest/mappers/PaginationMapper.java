package by.zemich.advertisementservice.infrastracture.input.rest.mappers;

import by.zemich.advertisementservice.domain.request.Pagination;
import by.zemich.advertisementservice.infrastracture.input.rest.data.request.PageRequestDto;

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
