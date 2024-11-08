package store.domain;

import static java.lang.Boolean.FALSE;
import static store.global.ErrorMessages.INVALID_INPUT_STOCK;
import static store.global.ErrorMessages.PRODUCT_NOT_FOUND;

import java.util.List;
import store.domain.promotion.Promotion;

public class Store {

    private final List<Item> items;
    private final List<Promotion> promotions;

    public Store(List<Item> items, List<Promotion> promotions) {
        this.items = items;
        this.promotions = promotions;
    }

    public boolean buyPromoItemNoDiscount(Stock stock, Item promotionalItem, List<Item> purchasedItems) {
        int count = stock.getStock();

        if (canApplyPromotion(count, promotionalItem)) {
            addPurchaseProduct(stock, promotionalItem, purchasedItems, FALSE);
            promotionalItem.updateStock(count);
            stock.minus(count);

            return true;
        }
        return false;
    }

    // TODO: 다른 곳으로 빼야 될듯 STATIC 아니면 ITEM 쪽으로
    public void isProductExists(Item nomalItem, Item promotionalItem) {
        if (nomalItem == null && promotionalItem == null) {
            throw new IllegalArgumentException(PRODUCT_NOT_FOUND.getMessage());
        }
    }

    public void isValidStock(Stock stock, Item promotionalItem, Item nomalItem) {
        Stock promotionStock = getProductStock(promotionalItem);
        Stock normalStock = getProductStock(nomalItem);

        if (promotionStock.getStock() + normalStock.getStock() < stock.getStock()) {
            throw new IllegalArgumentException(INVALID_INPUT_STOCK.getMessage());
        }
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

    public List<Item> getProducts() {
        return items;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public static void addPurchaseProduct(Stock stock, Item nomalItem, List<Item> purchasedItems, Boolean isFree) {
        Item purchasedItem = new Item(nomalItem, stock, isFree);
        purchasedItems.add(purchasedItem);
    }

    private boolean canApplyPromotion(int stock, Item promotionalItem) {
        return promotionalItem.getBuyStock() + promotionalItem.getGetStock() >= stock;
    }

    private static Stock getProductStock(Item item) {
        Stock promotionStock = null;
        if (item != null) {
            promotionStock = item.getStock();
        }
        return promotionStock;
    }
}
