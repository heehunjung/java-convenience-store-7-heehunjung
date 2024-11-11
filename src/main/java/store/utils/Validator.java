package store.utils;

import static store.global.ErrorMessages.INVALID_INPUT_STOCK_NEGATIVE;
import static store.global.ErrorMessages.INVALID_INPUT_YES_OR_NO;
import static store.global.InputConstant.DELIMITER;
import static store.global.InputConstant.NO_INPUT_BIG;
import static store.global.InputConstant.NO_INPUT_SMALL;
import static store.global.InputConstant.PRODUCT_STOCK_PATTERN;
import static store.global.ErrorMessages.DUPLICATE_INPUT_ERROR;
import static store.global.ErrorMessages.INVALID_INPUT_NULL_EMPTY;
import static store.global.ErrorMessages.INVALID_INPUT_PATTERN;
import static store.global.InputConstant.YES_INPUT_BIG;
import static store.global.InputConstant.YES_INPUT_SMALL;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Validator {

    public static void buyInputFormatValidator(String input) {
        nullOrEmptyValidator(input);

        List<String> splitInput = List.of(input.split(DELIMITER));

        for (String s : splitInput) {
            itemAndStockFormatValidator(s);
        }
    }

    public static void duplicatedNameValidator(List<String> input) {
        Set<String> uniqueNames = new HashSet<>(input);

        if (uniqueNames.size() != input.size()) {
            throw new IllegalArgumentException(DUPLICATE_INPUT_ERROR.getMessage());
        }
    }

    public static void itemAndStockFormatValidator(String input) {
        if (!input.matches(PRODUCT_STOCK_PATTERN)) {
            throw new IllegalArgumentException(INVALID_INPUT_PATTERN.getMessage());
        }
    }

    public static void YesOrNoValidator(String input) {
        nullOrEmptyValidator(input);

        if (!(input.equals(YES_INPUT_BIG) || input.equals(YES_INPUT_SMALL) || input.equals(NO_INPUT_BIG) || input.equals(NO_INPUT_SMALL))) {
            throw new IllegalArgumentException(INVALID_INPUT_YES_OR_NO.getMessage());
        }
    }

    public static void nullOrEmptyValidator(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(INVALID_INPUT_NULL_EMPTY.getMessage());
        }
    }

    public static void isPositiveValidator(int input) {
        if (input <= 0) {
            throw new IllegalArgumentException(INVALID_INPUT_STOCK_NEGATIVE.getMessage());
        }
    }

}
