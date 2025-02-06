package by.zemich.parser.infrastructure.scheduler;

import by.zemich.parser.application.service.*;
import by.zemich.parser.domain.model.Category;
import by.zemich.parser.infrastructure.clients.KufarClient;
import by.zemich.parser.infrastructure.clients.dto.CategoriesDto;
import by.zemich.parser.infrastructure.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class CategoriesScheduledService {
    private final KufarClient kufarClient;
    private final CategoryService categoryService;

    @Scheduled(initialDelay = 5_000, fixedDelay = 30_00_000)
    public void getAndUpdateCategories() {
        CategoriesDto categories = kufarClient.getCategories();
        categories.getCategories().stream()
                .map(categoryDto -> {
                    Category category = Mapper.mapToEntity(categoryDto);
                    categoryDto.getSubcategories().stream()
                            .map(Mapper::mapToEntity)
                            .forEach(category::addSubcategory);
                    return category;
                })
                .forEach(categoryService::save);

    }

}
