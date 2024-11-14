package store.utils;

import static store.global.ErrorMessages.INVALID_STATE_ERROR;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Converter {

    private Converter() {

    }

    public static LocalDate stringToLocalDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new IllegalStateException(INVALID_STATE_ERROR.getMessage());
        }
    }

}
