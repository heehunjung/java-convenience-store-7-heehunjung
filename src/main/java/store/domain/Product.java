package store.domain;

import static store.global.ErrorMessages.INVALID_INPUT_STOCK;
import static store.global.ErrorMessages.INVALID_PRODUCT_PRICE;
import static store.global.ErrorMessages.INVALID_PRODUCT_STOCK;

public class Product {

    private final String name;
    private final int price;
    private final Promotion promotion; //  변경 예정
    private int stock;

    public Product(String name, int price, int stock, Promotion promotion) {
        priceValidator(price);
        stockValidator(stock);

        this.name = name;
        this.price = price;
        this.promotion = promotion;
        this.stock = stock;
    }

    public void updateStock(int stock) {
        this.stock -= stock;
    }

    public void isStockAvailable(int stock) {
        if (stock > this.stock) {
            throw new IllegalArgumentException(INVALID_INPUT_STOCK.getMessage());
        }
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

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public int getPrice() {
        return price;
    }

    public Promotion getPromotion() {
        return promotion;
    }
}
