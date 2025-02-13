package by.zemich.advertisementservice.domain.valueobject;

public enum Type {
    BUY("куплю"), SELL("продам");

    private String value;

    Type(String value) {
    }

    public String getValue() {
        return value;
    }
}
