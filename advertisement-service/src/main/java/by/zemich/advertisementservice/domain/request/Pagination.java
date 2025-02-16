package by.zemich.advertisementservice.domain.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Pagination {
    private  int page;
    private  int size;
    private  String sortBy;
    private  boolean asc;
}
