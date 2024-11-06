package store.utils;

import static store.global.InputConstant.DELIMITER;
import static store.global.InputConstant.NO_INPUT;
import static store.global.InputConstant.PRODUCT_STOCK_PATTERN;
import static store.global.InputConstant.YES_INPUT;
import static store.global.InputErrorMessages.INVALID_INPUT_NULL_EMPTY;
import static store.global.InputErrorMessages.INVALID_INPUT_PATTERN;
import static store.global.InputErrorMessages.INVALID_YES_OR_NO_INPUT;

import java.util.List;

public class Validator {
    public static void purchaseInputFormatValidator(String input) {
        nullOrEmptyValidator(input);

        List<String> splitedInput = List.of(input.split(DELIMITER));

        for (String s : splitedInput) {
            productAndStockFormatValidator(s);
        }
    }

    public static void productAndStockFormatValidator(String input) {
        nullOrEmptyValidator(input);

        if (!input.matches(PRODUCT_STOCK_PATTERN)) {
            throw new IllegalArgumentException(INVALID_INPUT_PATTERN.getMessage());
        }
    }

    public static void YesOrNoValidator(String input) {
        nullOrEmptyValidator(input);

        if (!(input == YES_INPUT || input == NO_INPUT)) {
            throw new IllegalArgumentException(INVALID_YES_OR_NO_INPUT.getMessage());
        }
    }

    public static void nullOrEmptyValidator(String input) {
        if ( input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(INVALID_INPUT_NULL_EMPTY.getMessage());
        }
    }
}
