package store.global;

public enum ErrorMessages {

    INVALID_INPUT_PATTERN("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    INVALID_INPUT_STOCK("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    PRODUCT_NOT_FOUND("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    INVALID_INPUT_COMMON("잘못된 입력입니다. 다시 입력해 주세요."),

    INVALID_STATE_ERROR("잘못된 상태입니다. products.md 파일 또는 파일 입출력,파싱 로직을 확인해주세요.");

    private final String message;
    private final String code;

    private ErrorMessages(String message) {
        this.message = message;
        this.code = "[ERROR] ";
    }

    public String getMessage() {
        return code + message;
    }
}
