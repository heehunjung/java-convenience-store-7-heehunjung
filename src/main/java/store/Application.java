package store;

import java.io.IOException;
import store.controller.FrontController;
import store.controller.PromotionController;
import store.controller.product.ItemController;
import store.controller.product.PromotionItemController;
import store.domain.Store;

public class Application {
    public static void main(String[] args)  {
        // TODO: 프로그램 구현
        ItemController itemController = new ItemController();
        PromotionItemController promotionItemController = new PromotionItemController();
        PromotionController promotionController = new PromotionController();
        FrontController frontController = new FrontController(itemController, promotionItemController, promotionController);

        try {
            frontController.run();
        } catch (IOException e) {
            return;
        }
    }
}
