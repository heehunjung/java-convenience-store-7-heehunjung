package store.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ItemTest {

    @DisplayName("updateStock_테스트_01")
    @Test
    void updateStock_기능_테스트() {
        String name = "치킨";
        int price = 1000;
        Stock stock = new Stock(10);

        Item item = new Item(name, price, stock, null);
        item.updateStock(10);

        Assertions.assertThat(item.getStockCount())
                .isEqualTo(0);
    }

    @DisplayName("isStockAvailable_테스트_01")
    @Test
    void isStockAvailable_기능_테스트() {
        int inValidInput = 11;
        int validInput = 10;

        String name = "치킨";
        int price = 1000;
        Stock stock = new Stock(10);

        Item item = new Item(name, price, stock, null);

        Assertions.assertThatThrownBy(() -> item.isStockAvailable(inValidInput))
                .isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatNoException()
                .isThrownBy(() ->  item.isStockAvailable(validInput));
    }
}
