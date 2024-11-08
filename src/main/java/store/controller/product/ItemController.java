package store.controller.product;

import static java.lang.Boolean.FALSE;
import static store.domain.Store.addPurchaseProduct;

import java.util.List;
import store.domain.Item;
import store.domain.Stock;

public class ItemController {

    public void processProducts(Stock remainStock, Item nomalItem, List<Item> purchasedItems) {
        if (remainStock.getStock() > 0) {
            // todo : 흐름 상 nomalItem.getStockCount()가 무조건 remainStock 보다 크긴 한대 검증 해야함?
            nomalItem.updateStock(remainStock.getStock());
            addPurchaseProduct(remainStock, nomalItem, purchasedItems, FALSE);
        }
    }
}
