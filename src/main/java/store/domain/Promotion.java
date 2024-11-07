package store.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Promotion {
    private final String name;
    private final Range range;
    private final BuyGet buyGet;

    public Promotion( String name, BuyGet buyGet, Range range) {
        this.buyGet = buyGet;
        this.name = name;
        this.range = range;
    }

    public boolean checkDate(LocalDateTime now) {
        LocalDate date = now.toLocalDate();

        return range.isValidRange(date);
    }

    public int getBuyCount(int totalStock) {
        return buyGet.calculateBuyStock(totalStock);
    }
}
