package store.view;

import static store.global.OutputConstant.BUY_ITEM_INFO;
import static store.global.OutputConstant.EVENT_DISCOUNT;
import static store.global.OutputConstant.FINAL_AMOUNT;
import static store.global.OutputConstant.FREE_ITEM_INFO;
import static store.global.OutputConstant.FREE_ITEM_START;
import static store.global.OutputConstant.GREETING_MESSAGE;
import static store.global.OutputConstant.ITEM_INFORMATION_FORMAT;
import static store.global.OutputConstant.ITEM_INFORMATION_FORMAT_NO_PROMOTION;
import static store.global.OutputConstant.ITEM_INFORMATION_FORMAT_ZERO;
import static store.global.OutputConstant.MEMBERSHIP_DISCOUNT;
import static store.global.OutputConstant.RECEIPT_LINE;
import static store.global.OutputConstant.STARTING_MESSAGE;
import static store.global.OutputConstant.TOTAL_PRICE;

import java.util.List;
import store.domain.Item;
import store.domain.Receipt;
import store.domain.Store;

public class OutputView {

    private OutputView() {

    }

    public static void printGreeting() {
        System.out.println(GREETING_MESSAGE);
    }

    public static void printStoreInformation(Store store) {
        List<Item> items = store.getItems();
        for (Item item : items) {
            printInformation(item);
        }
    }

    public static void printReceipt(Receipt receipt) {
        System.out.println(STARTING_MESSAGE);
        for (Item item : receipt.getProducts()) {
            if (!item.getFree()) {
                System.out.printf(BUY_ITEM_INFO, item.getName(), receipt.calculateStock(item), item.getPrice());
            }
        }
        System.out.println(FREE_ITEM_START);
        for (Item item : receipt.getProducts()) {
            if (item.getFree()) {
                System.out.printf(FREE_ITEM_INFO, item.getName(), item.getStockCount());
            }
        }
        printPriceAndEndMessage(receipt);
    }

    private static void printPriceAndEndMessage(Receipt receipt) {
        System.out.println(RECEIPT_LINE);
        System.out.printf(TOTAL_PRICE, receipt.totalStock(), receipt.getTotalPrice());
        System.out.printf(EVENT_DISCOUNT, receipt.getPromotionPrice());
        System.out.printf(MEMBERSHIP_DISCOUNT, receipt.applyMembership());
        System.out.printf(FINAL_AMOUNT,
                receipt.getTotalPrice() - receipt.getPromotionPrice() - receipt.getMembershipPrice());
    }

    private static void printInformation(Item item) {
        if (item.getStockCount() == 0) {
            System.out.printf(ITEM_INFORMATION_FORMAT_ZERO, item.getName(), item.getPrice());
            return;
        }
        if (item.getPromotion() == null) {
            System.out.printf(ITEM_INFORMATION_FORMAT_NO_PROMOTION, item.getName(), item.getPrice(),
                    item.getStockCount());
            return;
        }
        System.out.printf(ITEM_INFORMATION_FORMAT, item.getName(), item.getPrice(), item.getStockCount(),
                item.getPromotion().getName());
    }
}
