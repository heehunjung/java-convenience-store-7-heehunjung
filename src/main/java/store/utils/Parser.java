package store.utils;

import static store.global.ErrorMessages.INVALID_INPUT_COMMON;
import static store.global.ErrorMessages.INVALID_STATE_ERROR;
import static store.global.InputConstant.DELIMITER;
import static store.global.InputConstant.PRODUCT_STOCK_DELIMITER;
import static store.utils.Validator.isPositiveValidator;

import java.util.List;
import java.util.Map;
import store.domain.Stock;

public class Parser {

    private Parser() {

    }

    public static int stringToInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalStateException(INVALID_STATE_ERROR.getMessage());
        }
    }

    public static List<String> splitWithBarDelimiter(String input) {
        return List.of(input.split(PRODUCT_STOCK_DELIMITER));
    }

    public static List<String> splitWithCommaDelimiter(String input) {
        return List.of(input.split(DELIMITER));
    }

    public static void itemAndStockParser(String input, Map<String, Stock> storeUserInput) {
        String trimmedInput = input.substring(1, input.length() - 1);
        List<String> splitInput = splitWithBarDelimiter(trimmedInput);
        getStringIntegerMap(splitInput, storeUserInput);
    }

    private static void getStringIntegerMap(List<String> splitInput, Map<String, Stock> storeUserInput) {
        String productName = splitInput.get(0);
        int stock;
        try {
            stock = Integer.parseInt(splitInput.get(1));
            isPositiveValidator(stock);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_INPUT_COMMON.getMessage());
        }
        storeUserInput.put(productName, new Stock(stock));
    }
}
