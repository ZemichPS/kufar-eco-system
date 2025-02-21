package by.zemich.advertisementservice.domain.valueobject;


public class CategoryAttribute {
    private Id id;
    private String name;

    public CategoryAttribute(Id id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryAttribute(String name, Id id) {
        this.name = name;
        this.id = id;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
