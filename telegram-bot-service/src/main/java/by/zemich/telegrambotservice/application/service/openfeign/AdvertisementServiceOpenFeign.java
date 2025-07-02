package by.zemich.telegrambotservice.application.service.openfeign;

import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient("getcategoriespath")
public interface AdvertisementServiceOpenFeign {

    List<String> getCategories();
}
