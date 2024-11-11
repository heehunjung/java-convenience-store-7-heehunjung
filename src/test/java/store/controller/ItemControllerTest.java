package store.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.assertj.core.internal.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.controller.product.ItemController;
import store.domain.Item;
import store.domain.Stock;
import store.domain.Store;
import store.domain.promotion.BuyGet;
import store.domain.promotion.Promotion;
import store.domain.promotion.Range;

public class ItemControllerTest {

    @DisplayName("processProducts_테스트_01")
    @Test
    void processProducts_기능_테스트() {

        Stock stock = new Stock(7);
        Item item = new Item("치킨", 1000, new Stock(10), null);
        List<Item> items = new ArrayList<>();

        ItemController itemController = new ItemController();
        Item purchasedItem = new Item(item, new Stock(0), Boolean.FALSE);

        itemController.processProducts(stock, purchasedItem, item, items);
        Assertions.assertThat(item.getStockCount()).isEqualTo(3);
    }

    @DisplayName("setProducts_테스트_01")
    @Test
    void 기능_테스트() {
        List<Promotion> promotions = promotionSetting();
        ItemController itemController = new ItemController();

        String line1 = "name,price,quantity,promotion";
        String line2 = "콜라,1000,10,탄산2+1";
        String line3 = "콜라,1000,10,null";
        String line4 = "사이다,1000,8,탄산2+1";
        String line5 = "사이다,1000,7,null";
        String line6 = "오렌지주스,1800,9,MD추천상품";
        List<String> lines = Arrays.asList(line1, line2, line3, line4, line5, line6);

        List<Item> expectedItems = Arrays.asList(
                new Item("콜라", 1000, new Stock(10), promotions.get(1)),
                new Item("콜라", 1000, new Stock(10), null),
                new Item("사이다", 1000, new Stock(8), promotions.get(1)),
                new Item("사이다", 1000, new Stock(7), null),
                new Item("오렌지주스", 1800, new Stock(9), promotions.get(2))
        );

        List<Item> items = itemController.setItems(lines, promotions);

        Assertions.assertThat(items.size()).isEqualTo(lines.size() - 1);
        Assertions.assertThat(items.get(0).getName()).isEqualTo("콜라");
        Assertions.assertThat(items.get(0).getPromotion().getName()).isEqualTo("탄산2+1");
        Assertions.assertThat(items.get(2).getName()).isEqualTo("사이다");
        Assertions.assertThat(items.get(2).getPromotion().getName()).isEqualTo("탄산2+1");
    }

    private List<Promotion> promotionSetting() {
        String start = "name,buy,get,start_date,end_date";
        String info1 = "탄산2+1,2,1,2024-01-01,2024-12-31";
        String info2 = "MD추천상품,1,1,2024-01-01,2024-12-31";
        String info3 = "반짝할인,1,1,2024-11-01,2024-11-30";
        List<String> infos = Arrays.asList(start, info1, info2, info3);

        PromotionController promotionController = new PromotionController();
        return promotionController.setPromotions(infos);
    }

}
