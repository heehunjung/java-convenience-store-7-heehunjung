package store.domain;

import static store.global.ErrorMessages.INVALID_INPUT_STOCK;
import static store.global.ErrorMessages.INVALID_PRODUCT_PRICE;

import java.time.LocalDateTime;
import java.util.Objects;
import store.domain.promotion.Promotion;

public class Item {

    private final String name;
    private final Boolean isFree;
    private final Promotion promotion;
    private final int price;
    private final Stock stock;
    private final int totalStock;

    public Item(String name, int price, Stock stock, Promotion promotion) {
        priceValidator(price);
        this.name = name;
        this.price = price;
        this.promotion = promotion;
        this.stock = stock;
        this.isFree = null;
        this.totalStock = stock.getStock();
    }

    public Item(Item item, Stock stock, Boolean isFree) {

        this.name = item.getName();
        this.price = item.getPrice();
        this.promotion = item.getPromotion();
        this.totalStock = item.getTotalStock();
        this.stock = stock;
        this.isFree = isFree;
    }

    public int getBuyStock() {
        return promotion.getBuyStock();
    }
    public int getTotalStock() {
        return totalStock;
    }
    public int getGetStock() {
        return promotion.getGetStock();
    }

    public int getTotalBuyStock(int stock, int currentStock) {
        return promotion.getTotalBuyStock(stock, currentStock);
    }

    //static?
    public int calculateFreeStock(int stock) {
        return promotion.calculateFreeStock(stock);
    }

    public void updateStock(int stock) {
        this.stock
                .minus(stock);
    }

    public void addStock(int stock) {
        this.stock
                .plus(stock);
    }

    public int getBuyStockByFreeStock(int stock) {
        int buyStock = promotion.getBuyStock();
        return buyStock * stock;
    }

    public boolean checkDate(LocalDateTime now) {
        return promotion.checkDate(now);
    }

    public void isStockAvailable(int stock) {
        if (!this.stock.compare(stock)) {
            throw new IllegalArgumentException(INVALID_INPUT_STOCK.getMessage());
        }
    }

    public int calculatePrice() {
        return price * stock.getStock();
    }

    public String getName() {
        return name;
    }

    public Stock getStock() {
        return stock;
    }

    public int getStockCount() {
        return stock.getStock();
    }


    public int getPrice() {
        return price;
    }

    public Boolean getFree() {
        return isFree;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    private void priceValidator(int price) {
        if (price <= 0) {
            throw new IllegalStateException(INVALID_PRODUCT_PRICE.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Item{" +
                "isFree=" + isFree +
                ", name='" + name + '\'' +
                ", promotion=" + promotion +
                ", price=" + price +
                ", stock=" + stock.getStock() +
                '}';
    }
}
