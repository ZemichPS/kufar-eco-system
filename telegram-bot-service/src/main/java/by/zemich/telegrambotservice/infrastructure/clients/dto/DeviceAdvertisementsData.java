package by.zemich.telegrambotservice.infrastructure.clients.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DeviceAdvertisementsData {
    private String brand;
    private String model;
    private String ram;
    private String memory;
    private List<BigDecimal> pricesInByn;
}
