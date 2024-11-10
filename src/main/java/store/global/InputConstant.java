package store.global;

public class InputConstant {

    private InputConstant() {
    }

    //INPUT PATTERN
    public static final String PRODUCT_STOCK_PATTERN = "\\[[가-힣]+-\\d+\\]";

    //DELIMITER
    public static final String DELIMITER = ",";
    public static final String PRODUCT_STOCK_DELIMITER = "-";

    //YES OR NO
    public static final String YES_INPUT = "Y";
    public static final String NO_INPUT = "N";
}
