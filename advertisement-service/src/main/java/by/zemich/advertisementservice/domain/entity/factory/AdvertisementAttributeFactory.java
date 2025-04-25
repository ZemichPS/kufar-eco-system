//package by.zemich.advertisementservice.domain.entity.factory;
//
//import by.zemich.advertisementservice.domain.entity.AdvertisementAttribute;
//import by.zemich.advertisementservice.domain.entity.Category;
//import by.zemich.advertisementservice.domain.valueobject.CategoryAttribute;
//import by.zemich.advertisementservice.domain.valueobject.Id;
//
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//public class AdvertisementAttributeFactory {
//
//
//    public static List<AdvertisementAttribute> get(Category category, Map<UUID, String> attributesMap) {
//        return attributesMap.entrySet().stream()
//                .map(entry -> {
//                    Id categoryAttributeId = new Id(entry.getKey());
//                    CategoryAttribute attribute = category.getAttributes().stream()
//                            .filter(attr -> categoryAttributeId.equals(attr.getId()))
//                            .findFirst().orElseThrow(); // TODO продумать
//                    String attributeValue = entry.getValue();
//                    return new AdvertisementAttribute(categoryAttributeId, attribute, attributeValue);
//                }).toList();
//    }
//}
