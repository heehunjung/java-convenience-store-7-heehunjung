package store.domain;

import java.util.List;


public class Receipt {

    private final List<Item> items;
    private int totalPrice;
    private int promotionPrice;
    private boolean membership;
    private int membershipPrice;

    public Receipt(List<Item> items, boolean membership) {
        this.items = items;
        this.membership = membership;
    }

    public Item findPromotionProduct(Item promotionItem) {
        for (Item item : items) {
            if (item.getName().equals(promotionItem.getName()) && (item.getFree())) {
                return item;
            }
        }
        Item newItem = new Item(promotionItem, new Stock(0), true);
        items.add(newItem);
        return newItem;
    }

    public void setMembership(boolean membership) {
        this.membership = membership;
    }

    public Item findPromotionProductWithNull(Item promotionItem) {
        for (Item item : items) {
            if (item.getName().equals(promotionItem.getName()) && (item.getPromotion() != null)) {
                return item;
            }
        }
        return null;
    }

    public void calculatePrice() {
        for (Item item : items) {
            totalPrice += item.calculatePrice();
            if (item.getFree()) {
                promotionPrice += item.calculatePrice();
            }
        }
    }

    public int calculateStock(Item target) {
        int count = 0;
        for (Item item : items) {
            if (item.getName().equals(target.getName())) {
                count += item.getStockCount();
            }
        }
        return count;
    }

    public int totalStock() {
        int total = 0;
        for (Item item : items) {
            total += item.getStockCount();
        }
        return total;
    }

    public boolean getMembership() {
        return membership;
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

    public int getMembershipPrice() {
        return membershipPrice;
    }

    public Integer applyMembership() {
        System.out.println();
        int discountPrice = 0;
        for (Item item : items) {
            if (item.getFree()) {
                int buyStock = item.getBuyStockByFreeStock(item.getStockCount());
                discountPrice += buyStock * item.getPrice();
            }
        }
        if (getMembership()) {
            return calculateMembershipPrice(discountPrice);
        }
        return 0;
    }

    public int calculateMembershipPrice(int discountPrice) {
        membershipPrice = Math.min((int) ((totalPrice - promotionPrice - discountPrice) * 0.3), 8000);
        return membershipPrice;
    }

}
