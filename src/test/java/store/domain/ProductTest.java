package store.domain;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductTest {

    @DisplayName("updateStock_테스트_01")
    @Test
    void 기능_테스트() {
        String name = "치킨";
        int price = 1000;
        int stock = 10;

        Product product = new Product(name,price,stock,null);
        product.update(10);

        Assertions.assertThat(product.getStock())
                .isEqualTo(0);
    }
}
