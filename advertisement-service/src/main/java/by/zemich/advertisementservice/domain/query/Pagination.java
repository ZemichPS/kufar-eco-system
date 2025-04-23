package by.zemich.advertisementservice.domain.query;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Pagination {
    private  int page;
    private  int size;
    private  String sortBy;
    private  boolean asc;
    private boolean onlyActive;
}
