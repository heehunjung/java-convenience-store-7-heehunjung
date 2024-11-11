package store.controller;

import static store.domain.Store.addPurchaseProduct;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import store.controller.product.ItemController;
import store.controller.product.PromotionItemController;
import store.domain.Item;
import store.domain.Stock;
import store.domain.Store;

public class StoreController {

    private final Store store;
    private final ItemController itemController;
    private final PromotionItemController  promotionItemController;

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

            store.isProductExists(nomalItem, promotionalItem);
            store.isValidStock(stock, promotionalItem, nomalItem);

            Item item = purchaseProcess(stock, promotionalItem, purchasedItems, nomalItem);

            addPurchaseProduct(purchasedItems, item);
        });
        return purchasedItems;
    }

    private Item purchaseProcess(Stock stock, Item promotionalItem, List<Item> purchasedItems, Item nomalItem) {
        Item item = processPromotionItems(stock, promotionalItem, purchasedItems, nomalItem);
        itemController.processItems(stock, item, nomalItem, purchasedItems);
        return item;
    }

    private Item processPromotionItems(Stock stock, Item promotionalItem, List<Item> purchasedItems, Item nomalItem) {
        Item item = promotionItemController.processPromotionItem(stock, promotionalItem, purchasedItems,
                store);

        if (item == null) {
            item = new Item(nomalItem, new Stock(0), Boolean.FALSE);
        }
        return item;
    }
}
