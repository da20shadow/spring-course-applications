package shop.models.enums;

public enum CategoryEnum {
    FRUITS ("Fruits"),
    VEGETABLES ("Vegetables"),
    MEAT ("Meat"),
    OTHER ("Other");

    private final String value;

    private CategoryEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
