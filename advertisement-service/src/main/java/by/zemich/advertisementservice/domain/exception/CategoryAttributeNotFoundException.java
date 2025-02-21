package by.zemich.advertisementservice.domain.exception;

public class CategoryAttributeNotFoundException extends EntityNotFoundException{
    public CategoryAttributeNotFoundException(String categoryAttributeUuid) {
        super("Category attribute UUID " + categoryAttributeUuid + " not found");
    }
}
