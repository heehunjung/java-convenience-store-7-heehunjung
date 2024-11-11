package store.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import store.view.InputView;

public class Receipt {
    private final List<Item> items;
    private int totalPrice;
    private int promotionPrice;
    private boolean isApplyMembership;
    private int membershipPrice;

    public Receipt(List<Item> items, boolean isApplyMembership) {
        this.items = items;
        this.isApplyMembership = isApplyMembership;
    }

    public void notifyStockForFree(Store store, Scanner scanner) {
        List<Item> newItems = new ArrayList<>(items);
        for (Item item : newItems) {
            if (!item.getFree() && item.getPromotion() != null) {
                compareBuyGet(store, this, item, scanner);
            }
        }

    }

    private void compareBuyGet(Store store, Receipt receipt, Item normalItem, Scanner sc) {
        Item promotionItem = receipt.findPromotionProduct(normalItem);
        Item promotionItemInStore = store.findPromotionProduct(normalItem.getName());
        Item itemInStore = store.findProduct(normalItem.getName());
        if (promotionItemInStore == null) {
            return;
        }
        // 구매한 것
        int currentStock = normalItem.getStockCount();
        int currentFreeStock = promotionItem.getStockCount();
        int currentPromotionStock = promotionItem.getBuyStockByFreeStock(currentFreeStock);
        int moreFreeStock = promotionItemInStore.getTotalBuyStock(currentStock - currentPromotionStock + 1,
                promotionItemInStore.getStockCount());
        String yesOrNO = "N";
        if (moreFreeStock > 0) {
            yesOrNO = InputView.getMoreFreeStock(sc, normalItem.getName(), 1);
        }
        if (yesOrNO.equals("Y") || yesOrNO.equals("y")) {
            promotionItem.addStock(1);
            // store
            promotionItemInStore.updateStock(1 + moreFreeStock);
            itemInStore.addStock(moreFreeStock);
        }
    }

    private Item findPromotionProduct(Item promotionItem) {
        for (Item item : items) {
            if (item.getName().equals(promotionItem.getName()) && (item.getFree())) {
                return item;
            }
        }
        Item newItem = new Item(promotionItem, new Stock(0), true);
        items.add(newItem);
        return newItem;
    }

    private Item findPromotionProductWithNull(Item promotionItem) {
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

    public void checkMembership() {
        for (Item item : items) {
            if (!item.getFree()) {
                Item promotionItem = findPromotionProductWithNull(item);
                compareBuyGet(item, promotionItem);
            }
        }
    }

    public void compareBuyGet(Item item, Item promotionItem) {
        if (promotionItem == null) {
            isApplyMembership = true;
            return;
        }
        int current = item.getStockCount() - item.getBuyStockByFreeStock(promotionItem.getStockCount());
        if (item.getStockCount() != item.getBuyStockByFreeStock(promotionItem.getStockCount())) {
            isApplyMembership = true;
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

    public boolean isApplyMembership() {
        return isApplyMembership;
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
        int discountPrice = 0 ;
        for (Item item : items) {
            if (item.getFree()) {
                int buyStock = item.getBuyStockByFreeStock(item.getStockCount());
                discountPrice += buyStock * item.getPrice();
            }
        }
        if (isApplyMembership()) {
            System.out.println("discountPrice = " + discountPrice);
            System.out.println("totalPrice = " + totalPrice);
            System.out.println("promotionPrice = " + promotionPrice);
            membershipPrice = Math.min((int) ((totalPrice - promotionPrice - discountPrice) * 0.3), 8000);
            return membershipPrice;
        }
        return 0;
    }

    public void notifyPurchasedInfo(Store store, Scanner scanner) {
        for (Item item : items) {
            if (!item.getFree()) {
               isNotApplyPromotion(scanner,store,item);
            }

        }
    }

    private void isNotApplyPromotion(Scanner sc, Store store, Item item) {
        Item promotionItemInStore = store.findPromotionProduct(item.getName());
        Item freeItem = findPromotionProductWithNull(item);
        String isPurchase = "Y";
        if (promotionItemInStore == null || freeItem == null) {
            return;
        }
        int getCount = promotionItemInStore.getBuyStock();
        int freeStock = freeItem.getStockCount();
        int currentBuyStockByPromotion = item.getBuyStockByFreeStock(freeStock);
        int currentBuyStockWithNoPromotion = item.getStockCount() - currentBuyStockByPromotion;
        if (getCount <= currentBuyStockWithNoPromotion) {
            isPurchase = InputView.getAgreeBuyWithNoPromotion(sc,item.getName(),currentBuyStockWithNoPromotion);
        }
        if (isPurchase.equals("n") || isPurchase.equals("N")) {
            notPurchase(store,item,currentBuyStockWithNoPromotion);
        }
    }

    private void notPurchase(Store store, Item item, int stock) {
        Item promotionItemInStore = store.findPromotionProduct(item.getName());
        Item itemInStore = store.findProduct(item.getName());

        int totalItemInStore = itemInStore.getTotalStock();

        int refundStock = Math.min(stock, totalItemInStore - itemInStore.getStockCount());
        itemInStore.addStock(refundStock);
        item.updateStock(refundStock);
        stock -= refundStock;

        if (stock > 0) {
            promotionItemInStore.addStock(stock);
            item.updateStock(stock);
        }
    }
}
