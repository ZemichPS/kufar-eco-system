package by.zemich.advertisementservice.domain.exception;

public class CategoryNotFoundException extends EntityNotFoundException{
    public CategoryNotFoundException(String categoryUuid) {
        super("Category UUID " + categoryUuid + " not found");
    }
}
