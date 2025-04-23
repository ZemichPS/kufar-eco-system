package by.zemich.advertisementservice.interfaces.rest.data.request;

import lombok.Data;

import java.util.UUID;

@Data
public class CategoryAttributeRequestDto {
    private String name;
    private UUID categoryId;
}
