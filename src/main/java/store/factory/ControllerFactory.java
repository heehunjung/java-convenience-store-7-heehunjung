package store.factory;

import store.controller.FrontController;
import store.controller.PromotionController;
import store.controller.product.ItemController;
import store.controller.product.PromotionItemController;

public class ControllerFactory {

    private ControllerFactory() {

    }

    public static FrontController createFrontController() {
        ItemController itemController = new ItemController();
        PromotionItemController promotionItemController = new PromotionItemController();
        PromotionController promotionController = new PromotionController();

        return new FrontController(itemController, promotionItemController, promotionController);
    }
}
