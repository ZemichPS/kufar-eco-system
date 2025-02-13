package by.zemich.advertisementservice.domain.valueobject;

public enum Condition {
    NEW("новое"),
    USED("б.у."),
    BROKEN("не исправно");

    private String conditionDescription;

    Condition(String conditionDescription) {}
    public String getConditionDescription() {
        return conditionDescription;
    }
}
