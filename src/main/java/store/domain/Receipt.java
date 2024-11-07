package store.domain;

import java.util.List;

public class Receipt {
    private final List<Product> products;
    private int totalPrice;
    private int membershipPrice;
    private int promotionPrice;
    private final boolean isCanUseMemberShip;

    public Receipt(List<Product> products, boolean isCanUseMemberShip) {
        this.products = products;
        this.isCanUseMemberShip = isCanUseMemberShip;
    }

    public boolean canUseMemberShip() {
        return isCanUseMemberShip;
    }

    public void calculatePrice() {
        for (Product product : products) {
            totalPrice += product.calculatePrice();

            if (product.getFree()) {
                promotionPrice += product.calculatePrice();
            }
        }
    }

    public boolean isCanUseMemberShip() {
        return isCanUseMemberShip;
    }

    public int getMembershipPrice() {
        return membershipPrice;
    }

    public List<Product> getProducts() {
        return products;
    }

    public int getPromotionPrice() {
        return promotionPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void applyMembership() {
        if (canUseMemberShip()) {
            membershipPrice = Math.min((int) ((totalPrice - promotionPrice) * 0.3), 8000);
        }
    }
}
