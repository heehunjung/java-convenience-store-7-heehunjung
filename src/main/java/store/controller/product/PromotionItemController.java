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
        // TODO : 여기도 메서드 줄이기
        if (promotionalItem.checkDate(DateTimes.now())) {
            Item item = store.buyPromoItemNoDiscount(stock, promotionalItem, purchasedItems);
            if (item != null) {
                return item;
            }

            promotionProcess(stock, promotionalItem, stockFreeItem, stockForPay);
            purchasedItem = new Item(promotionalItem, stockForPay, FALSE);
            addFreeProduct(stockFreeItem, promotionalItem, purchasedItems, TRUE);
        }

        if (stockForPay.getStock() == promotionalItem.getBuyStockByFreeStock(stockFreeItem.getStock())){
            store.setMembership(false);
        }

        return purchasedItem;
    }

    //TODO : 메서드 길이 줄이기
    private static void promotionProcess(Stock stock, Item promotionalItem, Stock stockFreeItem, Stock stockForPay) {
        int stockForPromotionItem = promotionalItem.getTotalBuyStock(stock.getStock(), promotionalItem.getStockCount());
        int freeStock = promotionalItem.calculateFreeStock(stockForPromotionItem);

        stockFreeItem.plus(freeStock);
        stockForPay.plus( stockForPromotionItem);

        promotionalItem.updateStock(stockForPromotionItem + freeStock);
        stock.minus (stockForPromotionItem + freeStock);
        int remainingStock = processRemainingPromotionStock(promotionalItem, stock);
        promotionalItem.updateStock(remainingStock);
        stock.minus(remainingStock);
        stockForPay.plus(remainingStock);

    }

    public static int processRemainingPromotionStock(Item promotionalItem, Stock remainStock) {
        return Math.min(promotionalItem.getStockCount(), remainStock.getStock());
    }
}
