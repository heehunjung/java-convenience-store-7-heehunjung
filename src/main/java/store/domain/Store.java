package store.domain;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static store.global.ErrorMessages.INVALID_INPUT_STOCK;
import static store.global.ErrorMessages.PRODUCT_NOT_FOUND;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import store.domain.promotion.Promotion;

public class Store {
    //TODO : Product -> Item

    // 필드 변수
    private final List<Item> items;
    private final List<Promotion> promotions;

    // 생성자
    public Store(List<Item> items, List<Promotion> promotions) {
        this.items = items;
        this.promotions = promotions;
    }

    // 공용 메서드
    public void run(Map<String, Stock> shoppingCarts) {
        shoppingCarts.forEach((product, stock) -> {
            Item nomalItem = findProduct(product);
            Item promotionalItem = findPromotionProduct(product);

            // 상품 존재 여부 확인
            isProductExists(nomalItem, promotionalItem);

            // 유효한 수량 확인
            isValidStock(stock, promotionalItem, nomalItem);

            List<Item> purchasedItems = new ArrayList<Item>();

            // 프로모션 제품 처리
            processPromotionalProducts(stock, promotionalItem, purchasedItems);

            // 일반 제품 처리
            processNormalProducts(stock, nomalItem, purchasedItems);
        });
    }

    //TODO : INDENT + 10즐
    public void processPromotionalProducts(Stock stock, Item promotionalItem, List<Item> purchasedItems) {
        Stock stockFreeItem = new Stock(0);
        Stock stockForPay = new Stock(0);

        if (promotionalItem == null) {
            return;
        }
        if (promotionalItem.checkDate(DateTimes.now())) {
            if (buyPromoItemNoDiscount(stock, promotionalItem, purchasedItems)) {
                return;
            }

            promotionProcess(stock, promotionalItem, stockFreeItem, stockForPay);

            addPurchaseProduct(stockForPay, promotionalItem, purchasedItems, FALSE);
            addPurchaseProduct(stockFreeItem, promotionalItem, purchasedItems, TRUE);
        }
    }

    private static void promotionProcess(Stock stock, Item promotionalItem, Stock stockFreeItem, Stock stockForPay) {
        int stockForPromotionItem = promotionalItem.getTotalBuyStock(stock.getStock());
        int remainingStock = processRemainingPromotionStock(promotionalItem);

        stockFreeItem.plus(promotionalItem.calculateFreeStock(stockForPromotionItem));
        stockForPay.plus(remainingStock + stockForPromotionItem);
        int total = stockForPay.getStock() + stockFreeItem.getStock();
        promotionalItem.updateStock(total);
        stock.minus(total);
    }

    public void processNormalProducts(Stock remainStock, Item nomalItem, List<Item> purchasedItems) {
        if (remainStock.getStock() > 0) {
            nomalItem.updateStock(remainStock.getStock());
            addPurchaseProduct(remainStock, nomalItem, purchasedItems, FALSE);
        }
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

    // private 메서드
    private static int processRemainingPromotionStock(Item promotionalItem) {
        return Math.max(promotionalItem.getStock()
                .getStock(), 0);
    }

    private void addPurchaseProduct(Stock stock, Item nomalItem, List<Item> purchasedItems, Boolean isFree) {
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
