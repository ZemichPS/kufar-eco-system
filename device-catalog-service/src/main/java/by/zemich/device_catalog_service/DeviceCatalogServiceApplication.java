package by.zemich.device_catalog_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DeviceCatalogServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeviceCatalogServiceApplication.class, args);
	}

}
