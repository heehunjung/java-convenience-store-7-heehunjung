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
    public static final String YES_INPUT_BIG = "Y";
    public static final String YES_INPUT_SMALL = "y";

    public static final String NO_INPUT_BIG = "N";
    public static final String NO_INPUT_SMALL = "n";

    //INPUT MESSAGE
    public static final String BUY_PRODUCT_MESSAGE = "\n\n구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    public static final String MEMBERSHIP_MESSAGE = "\n멤버십 할인을 받으시겠습니까? (Y/N)";
    public static final String ENDING_MESSAGE = "\n감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";
    public static final String NOTICE_FOR_FREE_STOCK_MESSAGE = "현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n";
    public static final String NOTICE_FOR_FREE_STOCK = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)";

}
