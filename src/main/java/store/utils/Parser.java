package store.utils;

import static store.global.ErrorMessages.INVALID_INPUT_STOCK_FORMAT;
import static store.global.InputConstant.DELIMITER;
import static store.global.InputConstant.PRODUCT_STOCK_DELIMITER;
import static store.utils.Validator.isPositiveValidator;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import store.domain.Stock;

public class Parser {

    //TODO : 에러메세지 변경
    public static int stringToInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("리소스 파일 형식을 확인해주세요. 프로그램을 종료합니다.");
        }
    }

    public static void itemAndStockParser(String input, Map<String, Stock> storeUserInput) {
        String trimmedInput = input.substring(1, input.length() - 1);
        List<String> splitInput = splitWithBarDelimiter(trimmedInput);

        getStringIntegerMap(splitInput,storeUserInput);
    }

    public static List<String> splitWithBarDelimiter(String input) {
        return List.of(input.split(PRODUCT_STOCK_DELIMITER));
    }

    public static List<String> splitWithCommaDelimiter(String input) {
        return List.of(input.split(DELIMITER));
    }

    public static boolean stringToBoolean(String input) {
        return Objects.equals(input, "y") || Objects.equals(input, "Y");
    }

    private static void getStringIntegerMap(List<String> splitInput, Map<String, Stock> storeUserInput) {
        String productName = splitInput.get(0);
        int stock;

        try {
            stock = Integer.parseInt(splitInput.get(1));
            isPositiveValidator(stock);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_INPUT_STOCK_FORMAT.getMessage());
        }

        storeUserInput.put(productName, new Stock(stock));
    }

}
