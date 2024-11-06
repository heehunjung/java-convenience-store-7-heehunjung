package store.domain;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductTest {

    @DisplayName("updateStock_테스트_01")
    @Test
    void updateStock_기능_테스트() {
        String name = "치킨";
        int price = 1000;
        int stock = 10;

        Product product = new Product(name, price, stock, null);
        product.updateStock(10);

        Assertions.assertThat(product.getStock())
                .isEqualTo(0);
    }

    @DisplayName("isStockAvailable_테스트_01")
    @Test
    void isStockAvailable_기능_테스트() {
        int inValidInput = 11;
        int validInput = 10;

        String name = "치킨";
        int price = 1000;
        int stock = 10;

        Product product = new Product(name, price, stock, null);

        Assertions.assertThatThrownBy(() -> product.isStockAvailable(inValidInput))
                .isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatNoException()
                .isThrownBy(() ->  product.isStockAvailable(validInput));
    }
}
