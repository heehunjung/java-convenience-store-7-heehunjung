package store.utils;

import static store.global.ErrorMessages.PARSING_FAIL_ERROR;
import static store.global.InputConstant.PRODUCT_STOCK_DELIMITER;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {

    public static Map<String, Integer> productStockParser(String input) {
        String trimmedInput = input.substring(1, input.length() - 1);
        List<String> splitInput = List.of(trimmedInput.split(PRODUCT_STOCK_DELIMITER));

        Map<String, Integer> result = getStringIntegerMap(splitInput);

        return result;
    }

    private static Map<String, Integer> getStringIntegerMap(List<String> splitInput) {
        String productName = splitInput.get(0);
        int stock;
        try {
            stock = Integer.parseInt(splitInput.get(1));
        } catch (NumberFormatException e) {
            // 프로그램 종료
            throw new NumberFormatException(PARSING_FAIL_ERROR.getMessage());
        }

        Map<String, Integer> result = new HashMap<>();
        result.put(productName, stock);

        return result;
    }
}
