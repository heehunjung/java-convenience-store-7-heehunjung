package store.domain;

import static store.global.ErrorMessages.INVALID_INPUT_STOCK;
import static store.global.ErrorMessages.INVALID_PRODUCT_PRICE;
import static store.global.ErrorMessages.INVALID_PRODUCT_STOCK;

import java.time.LocalDateTime;
import store.domain.promotion.Promotion;

public class Product {

    private final String name;
    private final Boolean isFree;
    private final Promotion promotion;
    private final int price;
    private int stock;

    public Product(String name, int price, int stock, Promotion promotion) {
        priceValidator(price);
        stockValidator(stock);

        this.name = name;
        this.price = price;
        this.promotion = promotion;
        this.stock = stock;
        this.isFree = null;
    }

    public Product(Product product, int stock ,Boolean isFree) {
        stockValidator(stock);

        this.name = product.getName();
        this.price = product.getPrice();
        this.promotion = product.getPromotion();
        this.stock = stock;
        this.isFree = isFree;
    }

    public int getTotalBuyStock(int stock) {
        return promotion.getTotalBuyStock(stock);
    }

    public int calculateFreeStock(int stock) {
        return promotion.calculateFreeStock(stock);
    }

    public void updateStock(int stock) {
        this.stock -= stock;
    }

    public boolean checkDate(LocalDateTime now) {
        return promotion.checkDate(now);
    }

    public void isStockAvailable(int stock) {
        if (stock > this.stock) {
            throw new IllegalArgumentException(INVALID_INPUT_STOCK.getMessage());
        }
    }

    public int calculatePrice() {
        return price * stock;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
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

    private void stockValidator(int stock) {
        if (stock <= 0) {
            throw new IllegalStateException(INVALID_PRODUCT_STOCK.getMessage());
        }
    }
}
