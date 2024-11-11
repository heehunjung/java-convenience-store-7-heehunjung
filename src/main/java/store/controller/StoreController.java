package store.controller;

import static store.domain.Item.isItemExists;
import static store.domain.Store.addPurchaseProduct;
import static store.global.ErrorMessages.INVALID_INPUT_STOCK;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import store.controller.product.ItemController;
import store.controller.product.PromotionItemController;
import store.domain.Item;
import store.domain.Stock;
import store.domain.Store;

public class StoreController {

    private final Store store;
    private final ItemController itemController;
    private final PromotionItemController promotionItemController;

    public StoreController(ItemController itemController, Store store,
                           PromotionItemController promotionItemController) {
        this.itemController = itemController;
        this.store = store;
        this.promotionItemController = promotionItemController;
    }

    public List<Item> buyProcess(Map<String, Stock> shoppingCarts) {
        List<Item> purchasedItems = new ArrayList<>();

        shoppingCarts.forEach((product, stock) -> {
            Item nomalItem = store.findProduct(product);
            Item promotionalItem = store.findPromotionProduct(product);

            isValidItemAndStock(stock, nomalItem, promotionalItem);
            process(stock, promotionalItem, purchasedItems, nomalItem);
        });
        return purchasedItems;
    }

    public void isValidStock(Stock stock, Item promotionalItem, Item normalItem) {
        int promotionStockCount = Optional.ofNullable(store.getProductStock(promotionalItem))
                .map(Stock::getStock)
                .orElse(0);
        int normalStockCount = Optional.ofNullable(store.getProductStock(normalItem))
                .map(Stock::getStock)
                .orElse(0);

        if (promotionStockCount + normalStockCount < stock.getStock()) {
            throw new IllegalArgumentException(INVALID_INPUT_STOCK.getMessage());
        }
    }

    private void process(Stock stock, Item promotionalItem, List<Item> purchasedItems, Item nomalItem) {
        Item item = purchaseProcess(stock, promotionalItem, purchasedItems, nomalItem);
        addPurchaseProduct(purchasedItems, item);
    }

    private void isValidItemAndStock(Stock stock, Item nomalItem, Item promotionalItem) {
        isItemExists(nomalItem, promotionalItem);
        isValidStock(stock, promotionalItem, nomalItem);
    }

    private Item purchaseProcess(Stock stock, Item promotionalItem, List<Item> purchasedItems, Item nomalItem) {
        Item item = processPromotionItems(stock, promotionalItem, purchasedItems, nomalItem);
        itemController.processItems(stock, item, nomalItem, purchasedItems);
        return item;
    }

    private Item processPromotionItems(Stock stock, Item promotionalItem, List<Item> purchasedItems, Item nomalItem) {
        Item item = promotionItemController.processPromotionItem(stock, promotionalItem, purchasedItems, store);

        if (item == null) {
            item = new Item(nomalItem, new Stock(0), Boolean.FALSE);
        }
        return item;
    }
}
