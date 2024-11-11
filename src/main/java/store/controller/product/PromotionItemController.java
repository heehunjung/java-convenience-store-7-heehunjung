package store.controller.product;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static store.domain.Store.addFreeProduct;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.List;
import store.domain.Item;
import store.domain.Stock;
import store.domain.Store;

public class PromotionItemController {

    public Item processPromotionItem(Stock stock, Item promotionalItem, List<Item> purchasedItems, Store store) {
        Stock stockFreeItem = new Stock(0);
        Stock stockForPay = new Stock(0);
        Item purchasedItem = null;
        if (promotionalItem == null) {
            return null;
        }
        if (promotionalItem.checkDate(DateTimes.now())) {
            Item item = store.buyPromoItemNoDiscount(stock, promotionalItem, purchasedItems);
            if (item != null) {
                return item;
            }
            purchasedItem = processPromotionItems(stock, promotionalItem, purchasedItems, stockFreeItem, stockForPay);
        }

        return purchasedItem;
    }

    public static int processRemainingPromotionStock(Item promotionalItem, Stock remainStock) {
        return Math.min(promotionalItem.getStockCount(), remainStock.getStock());
    }

    private Item processPromotionItems(Stock stock, Item promotionalItem, List<Item> purchasedItems, Stock stockFreeItem,
                                       Stock stockForPay) {
        Item purchasedItem;
        promotionProcess(stock, promotionalItem, stockFreeItem, stockForPay);
        purchasedItem = new Item(promotionalItem, stockForPay, FALSE);
        addFreeProduct(stockFreeItem, promotionalItem, purchasedItems, TRUE);
        return purchasedItem;
    }

    private static void promotionProcess(Stock stock, Item promotionalItem, Stock stockFreeItem, Stock stockForPay) {
        int stockForPromotionItem = promotionalItem.getTotalBuyStock(stock.getStock(), promotionalItem.getStockCount());
        int freeStock = promotionalItem.calculateFreeStock(stockForPromotionItem);

        updatePromotionItems(promotionalItem, stockFreeItem, stockForPay, freeStock, stockForPromotionItem);

        stock.minus(stockForPromotionItem + freeStock);
        int remainingStock = updateRemainItem(stock, promotionalItem, stockForPay);
        stock.minus(remainingStock);
    }

    private static int updateRemainItem(Stock stock, Item promotionalItem, Stock stockForPay) {
        int remainingStock = processRemainingPromotionStock(promotionalItem, stock);
        promotionalItem.updateStock(remainingStock);
        stockForPay.plus(remainingStock);
        return remainingStock;
    }

    private static void updatePromotionItems(Item promotionalItem, Stock stockFreeItem, Stock stockForPay, int freeStock,
                                             int stockForPromotionItem) {
        stockFreeItem.plus(freeStock);
        stockForPay.plus(stockForPromotionItem);
        promotionalItem.updateStock(stockForPromotionItem + freeStock);
    }
}
