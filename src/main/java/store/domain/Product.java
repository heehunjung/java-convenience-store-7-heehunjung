package store.domain;

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

    public int getStock() {
        return stock;
    }
}
