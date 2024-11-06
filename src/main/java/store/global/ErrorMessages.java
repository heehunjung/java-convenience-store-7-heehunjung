package store.global;

public enum ErrorMessages {
    // TODO : 그외 에러 [잘못된 입력입니다. 다시 입력해 주세요.] 다 이렇게 해야하나 ?
    // Input 에러
    INVALID_INPUT_PATTERN("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    INVALID_INPUT_NULL_EMPTY("빈 값을 입력하시면 안됩니다. 다시 입력해 주세요."),
    INVALID_INPUT_YES_OR_NO("Y 또는 N만 입력할 수 있습니다. 다시 입력해 주세요."),
    INVALID_INPUT_STOCK("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    DUPLICATE_INPUT_ERROR("중복된 상품명을 입력하셨습니다."),

    // TODO : 존재하지 않은 상품을 입력한 경우

    // 프로그램 내부 에러 -> 종료
    INVALID_PRODUCT_PRICE("상품 금액은 0원보다 커야 합니다. products.md 파일 또는 파일 입출력 로직을 확인해주세요."),
    INVALID_PRODUCT_STOCK("상품 수량은 0원보다 커야 합니다. products.md 파일 또는 파일 입출력 로직을 확인해주세요."),
    PARSING_FAIL_ERROR("파싱 과정에서 에러가 발생하였습니다.");

    private String message;
    private String code;

    private ErrorMessages(String message) {
        this.message = message;
        this.code = "[ERROR] ";
    }

    public String getMessage() {
        return code + message;
    }
}
