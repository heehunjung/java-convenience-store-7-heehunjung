package store.domain.promotion;

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

    public int getBuyStock() {
        return buyGet.getBuyStock();
    }

    public int getGetStock() {
        return buyGet.getGetStock();
    }

    public boolean checkDate(LocalDateTime now) {
        LocalDate date = now.toLocalDate();

        return range.isValidRange(date);
    }

    public int getTotalBuyStock(int totalStock) {
        return buyGet.calculateBuyStock(totalStock);
    }

    public String getName() {
        return name;
    }

    public int calculateFreeStock(int stock) {
        return buyGet.calculateGetStock(stock);
    }


}
