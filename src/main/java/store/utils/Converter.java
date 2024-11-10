package store.utils;

import java.time.LocalDate;

public class Converter {

    public static LocalDate stringToLocalDate(String date) {
        return LocalDate.parse(date);
    }

}
