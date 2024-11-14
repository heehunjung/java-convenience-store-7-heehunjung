package store.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import store.domain.Item;
import store.domain.Receipt;
import store.domain.Store;
import store.utils.Validator;
import store.view.InputView;

import static store.global.InputConstant.NO_INPUT_BIG;
import static store.global.InputConstant.YES_INPUT_BIG;
import static store.utils.Validator.isUserContinuingWithNo;

public class ReceiptController {

    private final Receipt receipt;

    public ReceiptController(Receipt receipt) {
        this.receipt = receipt;
    }

    public void notifyStockForFree(Store store, Scanner scanner) {
        List<Item> newItems = new ArrayList<>(receipt.getProducts());
        for (Item item : newItems) {
            if (!item.getFree() && item.getPromotion() != null) {
                isCanGetFreeStock(store, item, scanner);
            }
        }
    }

    public void notifyPurchasedInfo(Store store, Scanner scanner) {
        for (Item item : receipt.getProducts()) {
            if (!item.getFree()) {
                checkPromotions(scanner, store, item);
            }
        }
    }

    public void checkMembership() {
        for (Item item : receipt.getProducts()) {
            if (!item.getFree()) {
                Item promotionItem = receipt.findPromotionProductWithNull(item);
                compareBuyGet(item, promotionItem);
            }
        }
    }

    private void isCanGetFreeStock(Store store, Item normalItem, Scanner sc) {
        Item promotionItem = receipt.findPromotionProduct(normalItem);
        Item promotionItemInStore = store.findPromotionProduct(normalItem.getName());
        Item itemInStore = store.findProduct(normalItem.getName());
        if (promotionItemInStore == null) {
            return;
        }

        getFreeStock(normalItem, sc, promotionItem, promotionItemInStore, NO_INPUT_BIG, itemInStore);
    }

    private void getFreeStock(Item normalItem, Scanner sc, Item promotionItem, Item promotionItemInStore,
                              String yesOrNO,
                              Item itemInStore) {
        int moreFreeStock = getMoreFreeStock(normalItem, promotionItem, promotionItemInStore);
        if (moreFreeStock > 0) {
            yesOrNO = InputView.getMoreFreeStock(sc, normalItem.getName(), 1);
        }

        updateStock(yesOrNO, promotionItem, promotionItemInStore, moreFreeStock, itemInStore);
    }

    private int getMoreFreeStock(Item normalItem, Item promotionItem, Item promotionItemInStore) {
        int currentStock = normalItem.getStockCount();
        int currentFreeStock = promotionItem.getStockCount();
        int currentPromotionStock = promotionItem.getBuyStockByFreeStock(currentFreeStock);

        return promotionItemInStore.getTotalBuyStock(currentStock - currentPromotionStock + 1,
                promotionItemInStore.getStockCount());
    }

    private void updateStock(String yesOrNO, Item promotionItem, Item promotionItemInStore, int moreFreeStock,
                             Item itemInStore) {
        if (Validator.isUserContinuing(yesOrNO)) {
            promotionItem.addStock(1);
            promotionItemInStore.updateStock(1 + moreFreeStock);
            itemInStore.addStock(moreFreeStock);
        }
    }

    private void checkPromotions(Scanner sc, Store store, Item item) {
        Item promotionItemInStore = store.findPromotionProduct(item.getName());
        Item freeItem = receipt.findPromotionProductWithNull(item);

        if (promotionItemInStore == null || freeItem == null) {
            return;
        }

        isNoPromotionItems(sc, store, item, promotionItemInStore, freeItem, YES_INPUT_BIG);
    }

    private void isNoPromotionItems(Scanner sc, Store store, Item item, Item promotionItemInStore, Item freeItem,
                           String isPurchase) {
        int getCount = promotionItemInStore.getBuyStock();
        int freeStock = freeItem.getStockCount();
        int currentBuyStockByPromotion = item.getBuyStockByFreeStock(freeStock);
        int currentBuyStockWithNoPromotion = item.getStockCount() - currentBuyStockByPromotion;

        if (getCount <= currentBuyStockWithNoPromotion) {
            isPurchase = InputView.getAgreeBuyWithNoPromotion(sc, item.getName(), currentBuyStockWithNoPromotion);
        }
        refundProcess(store, item, isPurchase, currentBuyStockWithNoPromotion);
    }

    private void refundProcess(Store store, Item item, String isPurchase, int currentBuyStockWithNoPromotion) {
        if (isUserContinuingWithNo(isPurchase)) {
            notPurchase(store, item, currentBuyStockWithNoPromotion);
        }
    }

    private void notPurchase(Store store, Item item, int stock) {
        Item promotionItemInStore = store.findPromotionProduct(item.getName());
        Item itemInStore = store.findProduct(item.getName());

        stock = calculateUpdateStock(item, stock, itemInStore);

        if (stock > 0) {
            promotionItemInStore.addStock(stock);
            item.updateStock(stock);
        }
    }

    private int calculateUpdateStock(Item item, int stock, Item itemInStore) {
        int totalItemInStore = itemInStore.getTotalStock();

        int refundStock = Math.min(stock, totalItemInStore - itemInStore.getStockCount());
        itemInStore.addStock(refundStock);
        item.updateStock(refundStock);
        stock -= refundStock;
        return stock;
    }

    private void compareBuyGet(Item item, Item promotionItem) {
        if (promotionItem == null) {
            receipt.setMembership(true);
            return;
        }
        if (item.getStockCount() != item.getBuyStockByFreeStock(promotionItem.getStockCount())) {
            receipt.setMembership(true);
        }
    }
}
