package store.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Converter {
    //TODO: 에러메세지 처리
    public static LocalDate stringToLocalDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new IllegalStateException("리소스 파일의 날짜 형식을 확인해주세요. 프로그램을 종료합니다.");
        }
    }

}
