package store.global;

public class OutputConstant {

    private OutputConstant() {

    }

    //STORE OUTPUT
    public static final String GREETING_MESSAGE = "안녕하세요. W편의점입니다.\n"
            + "현재 보유하고 있는 상품입니다.";
    public static final String ITEM_INFORMATION_FORMAT = "\n- %s %,d원 %d개 %s";
    public static final String ITEM_INFORMATION_FORMAT_ZERO = "\n- %s %,d원 재고 없음";
    public static final String ITEM_INFORMATION_FORMAT_NO_PROMOTION = "\n- %s %,d원 %d개 ";

    //RECEIPT OUTPUT
    public static final String STARTING_MESSAGE = """
        ==============W 편의점================
        상품명\t\t\t수량\t\t금액""";
    public static final String BUY_ITEM_INFO = "%-8s\t\t%d \t\t%,d\n";
    public static final String FREE_ITEM_START = "=============증\t정===============";
    public static final String FREE_ITEM_INFO = "%-8s\t\t%d\n";
    public static final String RECEIPT_LINE = "====================================";
    public static final String TOTAL_PRICE = "총구매액\t\t\t%d\t\t%,d\n";
    public static final String EVENT_DISCOUNT = "행사할인\t\t\t\t\t-%,d";
    public static final String MEMBERSHIP_DISCOUNT = "멤버십할인\t\t\t\t-%,d\n";
    public static final String FINAL_AMOUNT = "내실돈\t\t\t\t\t%,d\n";

}
