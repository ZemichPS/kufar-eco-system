package by.zemich.advertisementservice.infrastracture.input.rest.data.request;

import lombok.Data;

@Data
public class PageRequestDto {
    private  int page;
    private  int size;
    private  String sortBy;
    private  boolean asc;
}
