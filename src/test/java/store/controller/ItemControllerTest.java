package store.controller;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.controller.product.ItemController;
import store.domain.Item;
import store.domain.Stock;

public class ItemControllerTest {

    @DisplayName("processProducts_테스트_01")
    @Test
    void processProducts_기능_테스트() {
        Stock stock = new Stock(10);
        Item item = new Item("치킨", 1000, new Stock(10), null);
        List<Item> items = new ArrayList<>();
        ItemController itemController = new ItemController();

        itemController.processProducts(stock, item, items);
        Assertions.assertThat(items.size()).isEqualTo(1);
        Assertions.assertThat(items.getFirst().getName()).isEqualTo(item.getName());

    }
}
