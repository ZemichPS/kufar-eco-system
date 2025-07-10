package by.zemich.telegrambotservice.application.service.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("getcategoriespath")
public interface AdvertisementServiceOpenFeign {

    @GetMapping
    List<String> getCategories();
}
