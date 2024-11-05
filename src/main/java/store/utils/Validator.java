package store.utils;

import static store.global.InputConstant.DELIMITER;
import static store.global.InputConstant.INPUT_PATTERN;
import static store.global.InputConstant.NO_INPUT;
import static store.global.InputConstant.PRODUCT_STOCK_PATTERN;
import static store.global.InputConstant.YES_INPUT;
import static store.global.InputErrorMessages.INVALID_INPUT_PATTERN;

import java.util.List;

public class Validator {
    public static void purchaseInputFormatValidator(String input) {
        List<String> splitedInput = List.of(input.split(DELIMITER));

        for (String s : splitedInput) {
            productAndStockFormatValidator(s);
        }
    }

    public static void productAndStockFormatValidator(String input) {
        if (!input.matches(PRODUCT_STOCK_PATTERN)) {
            throw new IllegalArgumentException(INVALID_INPUT_PATTERN.getMessage());
        }
    }

    public static void YesOrNoValidator(String input) {
        if (!(input == YES_INPUT || input == NO_INPUT)) {
            throw new IllegalArgumentException();
        }
    }
}
