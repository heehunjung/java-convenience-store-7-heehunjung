package store.domain;

import static java.lang.Boolean.FALSE;
import static store.global.ErrorMessages.INVALID_INPUT_STOCK;
import static store.global.ErrorMessages.PRODUCT_NOT_FOUND;

import java.util.List;
import java.util.Optional;
import store.domain.promotion.Promotion;

public class Store {

    private final List<Item> items;
    private final List<Promotion> promotions;
    private boolean isMembership;

    public Store(List<Item> items, List<Promotion> promotions) {
        this.items = items;
        this.promotions = promotions;
        this.isMembership = true;
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

    // TODO: 다른 곳으로 빼야 될듯 STATIC 아니면 ITEM 쪽으로
    public void isProductExists(Item nomalItem, Item promotionalItem) {
        if (nomalItem == null && promotionalItem == null) {
            throw new IllegalArgumentException(PRODUCT_NOT_FOUND.getMessage());
        }
    }

    public void isValidStock(Stock stock, Item promotionalItem, Item normalItem) {
        int promotionStockCount = Optional.ofNullable(getProductStock(promotionalItem))
                .map(Stock::getStock)
                .orElse(0);
        int normalStockCount = Optional.ofNullable(getProductStock(normalItem))
                .map(Stock::getStock)
                .orElse(0);

        if (promotionStockCount + normalStockCount < stock.getStock()) {
            throw new IllegalArgumentException(INVALID_INPUT_STOCK.getMessage());
        }
    }

    public List<Item> getItems() {
        return items;
    }

    public Item findProduct(String input) {
        for (Item item : items) {
            if (item.getName().equals(input) && item.getPromotion() == null) {
                return item;
            }
        }
        return null;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public Item findPromotionProduct(String input) {
        for (Item item : items) {
            if (item.getName().equals(input) && item.getPromotion() != null) {
                return item;
            }
        }
        return null;
    }

    public boolean isMembership() {
        return isMembership;
    }

    public List<Item> getProducts() {
        return items;
    }

    public void setMembership(boolean isMembership) {
        this.isMembership = isMembership;
    }

    public static void addFreeProduct(Stock stock, Item promotionItem, List<Item> purchasedItems, Boolean isFree) {
        int stockCount = stock.getStock();

        if (stockCount > 0) {
            Item purchasedItem = new Item(promotionItem,new Stock(stockCount) , isFree);
            stock.minus(stock.getStock());
            purchasedItems.add(purchasedItem);
        }
    }

    public static void addPurchaseProduct(List<Item> purchasedItems, Item purchasedItem) {
        purchasedItems.add(purchasedItem);
    }

    private boolean canApplyPromotion(int stock, Item promotionalItem) {
        return promotionalItem.getBuyStock() > stock;
    }

    private static Stock getProductStock(Item item) {
        Stock promotionStock = null;
        if (item != null) {
            promotionStock = item.getStock();
        }
        return promotionStock;
    }
}
