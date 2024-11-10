package store.utils;

import static store.global.ErrorMessages.INVALID_INPUT_STOCK_FORMAT;
import static store.global.InputConstant.DELIMITER;
import static store.global.InputConstant.PRODUCT_STOCK_DELIMITER;
import static store.utils.Validator.isPositiveValidator;

import java.util.List;
import java.util.Map;

public class Parser {

    public static void itemAndStockParser(String input, Map<String, Integer> storeUserInput) {
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

    private static void getStringIntegerMap(List<String> splitInput, Map<String, Integer> storeUserInput) {
        String productName = splitInput.get(0);
        int stock;

        try {
            stock = Integer.parseInt(splitInput.get(1));
            isPositiveValidator(stock);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_INPUT_STOCK_FORMAT.getMessage());
        }

        storeUserInput.put(productName, stock);
    }

}
