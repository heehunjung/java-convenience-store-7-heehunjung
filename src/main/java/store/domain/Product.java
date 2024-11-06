package store.domain;

import static store.global.ErrorMessages.INVALID_INPUT_STOCK;

public class Product {

    private final String name;

    private final int price;
    Object promotion; //  변경 예정
    private int stock;

    public Product(String name, int price, int stock, Object promotion) {
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

    public int getStock() {
        return stock;
    }

    public int getPrice() {
        return price;
    }

    public Object getPromotion() {
        return promotion;
    }

}
