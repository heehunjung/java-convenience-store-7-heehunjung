package store.controller.product;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static store.domain.Store.addPurchaseProduct;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.List;
import store.domain.Item;
import store.domain.Stock;
import store.domain.Store;

public class PromotionItemController {

    private final Store store;

    public PromotionItemController(Store store) {
        this.store = store;
    }

    public void processPromotionItem(Stock stock, Item promotionalItem, List<Item> purchasedItems) {
        Stock stockFreeItem = new Stock(0);
        Stock stockForPay = new Stock(0);

        if (promotionalItem == null) {
            return;
        }
        // TODO : 여기도 메서드 줄이기
        if (promotionalItem.checkDate(DateTimes.now())) {
            if (store.buyPromoItemNoDiscount(stock, promotionalItem, purchasedItems)) {
                return;
            }

            promotionProcess(stock, promotionalItem, stockFreeItem, stockForPay);
            addPurchaseProduct(stockForPay, promotionalItem, purchasedItems, FALSE);
            addPurchaseProduct(stockFreeItem, promotionalItem, purchasedItems, TRUE);
        }
    }

    private static void promotionProcess(Stock stock, Item promotionalItem, Stock stockFreeItem, Stock stockForPay) {
        int stockForPromotionItem = promotionalItem.getTotalBuyStock(stock.getStock());
        int freeStock =  promotionalItem.calculateFreeStock(stockForPromotionItem);

        stockFreeItem.plus(promotionalItem.calculateFreeStock(stockForPromotionItem));
        promotionalItem.updateStock(freeStock + stockForPromotionItem);

        int remainingStock = processRemainingPromotionStock(promotionalItem);

        stockForPay.plus(remainingStock + stockForPromotionItem);
        promotionalItem.updateStock(remainingStock);

        int total = stockForPay.getStock() + stockFreeItem.getStock();

        stock.minus(total);
    }

    private static int processRemainingPromotionStock(Item promotionalItem) {
        return Math.max(promotionalItem.getStockCount(), 0);
    }
}
