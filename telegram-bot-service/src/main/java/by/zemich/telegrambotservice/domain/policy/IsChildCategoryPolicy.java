//package by.zemich.telegrambotservice.domain.policy;
//
//import by.zemich.kufar.application.service.SubCategoryService;
//import by.zemich.kufar.domain.model.KufarAdvertisement;
//import by.zemich.kufar.domain.model.Subcategory;
//import by.zemich.kufar.domain.policy.api.Policy;
//
//import java.util.Optional;
//
//public class IsChildCategoryPolicy implements Policy<KufarAdvertisement> {
//
//    private final String parentCategory;
//    private final SubCategoryService subCategoryService;
//
//    public IsChildCategoryPolicy(String parentCategory, SubCategoryService subCategoryService) {
//        this.parentCategory = parentCategory;
//        this.subCategoryService = subCategoryService;
//    }
//
//    @Override
//    public boolean isSatisfiedBy(KufarAdvertisement advertisement) {
//        String advertisementCategory = advertisement.getCategory();
//        Optional<Subcategory> subcategory = subCategoryService.findById(advertisementCategory);
//        return subcategory.map(value -> value.getCategory().getId().equals(parentCategory)).orElse(false);
//    }
//}
