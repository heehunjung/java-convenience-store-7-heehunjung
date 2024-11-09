package store.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.controller.product.ItemController;
import store.controller.product.PromotionItemController;
import store.domain.Item;
import store.domain.Stock;
import store.domain.Store;
import store.domain.promotion.BuyGet;
import store.domain.promotion.Promotion;
import store.domain.promotion.Range;

public class PromotionItemControllerTest {

    @DisplayName("processPromotionItem_테스트_01")
    @Test
    void processPromotionItem_기능_테스트_01()  {
        List<Item> items = new ArrayList<>();
        Store store = createStoreWithTestProductsAndPromotions();

        PromotionItemController controller = new PromotionItemController(store);
        Item promotionItem = store.findPromotionProduct("치킨");
        System.out.println(promotionItem.getPromotion().getName()+" , " + promotionItem.getName() + " , " + promotionItem.getStockCount());
        Stock stock = new Stock(5);

        controller.processPromotionItem(stock,promotionItem,items);
        for (Item item : items) {
            System.out.println(item.getPromotion().getName()+" , "+item.getStockCount()+" , "+item.getName() + " , " + item.getFree());
        }
        Assertions.assertThat(stock.getStock()).isEqualTo(0);
        Assertions.assertThat(items.size()).isEqualTo(2);

    }

    private Store createStoreWithTestProductsAndPromotions() {
        LocalDate startTime = LocalDate.of(2024, 10, 1);
        LocalDate endTime = LocalDate.of(2024, 12, 30);

        Promotion threePlusOne = new Promotion("3+1", new BuyGet(3, 1), new Range(startTime, endTime));
        Promotion twoPlusOne = new Promotion("2+1", new BuyGet(2, 1), new Range(startTime, endTime));

        Item item1 = new Item("치킨", 5000, new Stock(5), threePlusOne);
        Item item2 = new Item("치킨", 1000, new Stock(10), null);
        Item item3 = new Item("콜라", 2000, new Stock(15), twoPlusOne);
        Item item4 = new Item("사이다", 3000, new Stock(20), null);

        List<Item> items = List.of(item1, item2, item3, item4);
        List<Promotion> promotions = List.of(threePlusOne, twoPlusOne);

        return new Store(items, promotions);
    }
}
