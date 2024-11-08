package store.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import store.controller.product.ItemController;
import store.controller.product.PromotionItemController;
import store.domain.Item;
import store.domain.Stock;
import store.domain.Store;

public class FrontController {

    private final Store store;
    private final PromotionItemController promotionController;
    private final ItemController itemController;

    public FrontController(Store store, PromotionItemController promotionController, ItemController itemController) {
        this.store = store;
        this.promotionController = promotionController;
        this.itemController = itemController;
    }

    public void run(Map<String, Stock> shoppingCarts) {
        shoppingCarts.forEach((product, stock) -> {
            Item nomalItem = store.findProduct(product);
            Item promotionalItem = store.findPromotionProduct(product);

            // 상품 존재 여부 확인
            store.isProductExists(nomalItem, promotionalItem);

            // 유효한 수량 확인
            store.isValidStock(stock, promotionalItem, nomalItem);

            List<Item> purchasedItems = new ArrayList<Item>();

            // 프로모션 제품 처리
            promotionController.processPromotionItem(stock, promotionalItem, purchasedItems);

            // 일반 제품 처리
            itemController.processProducts(stock, nomalItem, purchasedItems);
        });
    }
}
