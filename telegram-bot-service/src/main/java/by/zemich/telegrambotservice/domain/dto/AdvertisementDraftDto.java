package by.zemich.telegrambotservice.domain.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class AdvertisementDraftDto {

    public AdvertisementDraftDto(UUID userUuid){
        this.userUuid = userUuid;
    }
    private UUID userUuid;
    private String vendorName;
    private String modelName;
    private String category;
    private String condition;
    private String price;
    private String comment;
    private String photoName;
    private List<String> attributes;
}
