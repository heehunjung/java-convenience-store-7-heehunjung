package store.domain;

import static java.lang.Boolean.FALSE;

import java.util.List;

public class Store {

    private final List<Item> items;

    public Store(List<Item> items) {
        this.items = items;
    }

    public Item buyPromoItemNoDiscount(Stock stock, Item promotionalItem, List<Item> purchasedItems) {
        int count = stock.getStock();
        if (canApplyPromotion(count, promotionalItem)) {
            Item item = new Item(promotionalItem, new Stock(count), FALSE);
            promotionalItem.updateStock(count);
            stock.minus(count);
            return item;
        }
        return null;
    }

    public Item findProduct(String input) {
        for (Item item : items) {
            if (item.getName().equals(input) && item.getPromotion() == null) {
                return item;
            }
        }
        return null;
    }

    public Item findPromotionProduct(String input) {
        for (Item item : items) {
            if (item.getName().equals(input) && item.getPromotion() != null) {
                return item;
            }
        }
        return null;
    }

    public Stock getProductStock(Item item) {
        return item != null ? item.getStock() : null;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public static void addFreeProduct(Stock stock, Item promotionItem, List<Item> purchasedItems, Boolean isFree) {
        int stockCount = stock.getStock();
        if (stockCount > 0) {
            Item purchasedItem = new Item(promotionItem, new Stock(stockCount), isFree);
            stock.minus(stockCount);
            purchasedItems.add(purchasedItem);
        }
    }

    public static void addPurchaseProduct(List<Item> purchasedItems, Item purchasedItem) {
        purchasedItems.add(purchasedItem);
    }

    private boolean canApplyPromotion(int stock, Item promotionalItem) {
        return promotionalItem.getBuyStock() > stock;
    }
}
