package store.utils;

import static store.global.ErrorMessages.INVALID_INPUT_YES_OR_NO;
import static store.global.InputConstant.DELIMITER;
import static store.global.InputConstant.NO_INPUT;
import static store.global.InputConstant.PRODUCT_STOCK_PATTERN;
import static store.global.InputConstant.YES_INPUT;
import static store.global.ErrorMessages.DUPLICATE_INPUT_ERROR;
import static store.global.ErrorMessages.INVALID_INPUT_NULL_EMPTY;
import static store.global.ErrorMessages.INVALID_INPUT_PATTERN;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Validator {

    public static void purchaseInputFormatValidator(String input) {
        nullOrEmptyValidator(input);

        List<String> splitedInput = List.of(input.split(DELIMITER));

        for (String s : splitedInput) {
            productAndStockFormatValidator(s);
        }
    }

    public static void duplicatedNameValidator(List<String> input) {
        Set<String> uniqueNames = new HashSet<>(input);

        if (uniqueNames.size() != input.size()) {
            throw new IllegalArgumentException(DUPLICATE_INPUT_ERROR.getMessage());
        }
    }

    public static void productAndStockFormatValidator(String input) {
        if (!input.matches(PRODUCT_STOCK_PATTERN)) {
            throw new IllegalArgumentException(INVALID_INPUT_PATTERN.getMessage());
        }
    }

    public static void YesOrNoValidator(String input) {
        nullOrEmptyValidator(input);

        if (!(input.equals(YES_INPUT) || input.equals(NO_INPUT))) {
            throw new IllegalArgumentException(INVALID_INPUT_YES_OR_NO.getMessage());
        }
    }

    public static void nullOrEmptyValidator(String input) {
        if ( input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(INVALID_INPUT_NULL_EMPTY.getMessage());
        }
    }

}
