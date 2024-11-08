package store.domain;

import java.util.List;

public class Receipt {
    private final List<Item> items;
    private int totalPrice;
    private int membershipPrice;
    private int promotionPrice;
    private final boolean isCanUseMemberShip;

    public Receipt(List<Item> items, boolean isCanUseMemberShip) {
        this.items = items;
        this.isCanUseMemberShip = isCanUseMemberShip;
    }

    public boolean canUseMemberShip() {
        return isCanUseMemberShip;
    }

    public void calculatePrice() {
        for (Item item : items) {
            totalPrice += item.calculatePrice();

            if (item.getFree()) {
                promotionPrice += item.calculatePrice();
            }
        }
    }

    public boolean isCanUseMemberShip() {
        return isCanUseMemberShip;
    }

    public int getMembershipPrice() {
        return membershipPrice;
    }

    public List<Item> getProducts() {
        return items;
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
