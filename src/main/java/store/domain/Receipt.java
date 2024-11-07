package store.domain;

import java.util.List;

public class Receipt {
    private final List<Product> products;
    private int totalPrice;
    private int memberShipPrice;
    private int promotionPrice;
    private boolean isCanUseMemberShip;

    public Receipt(List<Product> products) {
        this.products = products;
    }

    public boolean canUseMemberShip() {
        return isCanUseMemberShip;
    }
}
