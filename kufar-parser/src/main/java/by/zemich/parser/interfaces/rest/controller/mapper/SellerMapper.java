package by.zemich.parser.interfaces.rest.controller.mapper;

import by.zemich.parser.domain.model.Advertisement;
import by.zemich.parser.domain.model.Seller;
import by.zemich.parser.interfaces.rest.controller.dto.AdOwnerDto;
import by.zemich.parser.interfaces.rest.controller.dto.response.DeviceAdvertisementDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SellerMapper {
    public static AdOwnerDto mapToDto(Seller seller) {
        return AdOwnerDto.builder()
                .id(seller.getId())
                .firstFeedbackCreatedAt(seller.getFirstFeedbackCreatedAt())
                .rate(seller.getRate())
                .build();
    }
}
