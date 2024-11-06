package store.global;

public enum ErrorMessages {
    INVALID_INPUT_PATTERN("올바르지 않은 형식으로 입력했습니다."),
    INVALID_INPUT_NULL_EMPTY("빈 값을 입력하시면 안됩니다."),
    INVALID_YES_OR_NO_INPUT("Y 또는 N만 입력할 수 있습니다."),

    DUPLICATE_INPUT_ERROR("중복된 상품명을 입력하셨습니다."),

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
