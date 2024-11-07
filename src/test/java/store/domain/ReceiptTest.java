package store.domain;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReceiptTest {

    @DisplayName("canUseMembership_테스트_01")
    @Test
    void canUseMembership_기능_테스트() {
        List<Product> products = getProducts();

        Receipt receipt = new Receipt(products);

        Assertions.assertThat(receipt.canUseMemberShip())
                .isTrue();
    }

    private List<Product> getProducts() {
        Promotion promotion = new Promotion("test", null, null);

        Product product1 = new Product("치킨", 5000, 10, promotion);
        Product product2 = new Product("치킨", 1000, 10, null);
        Product product3 = new Product("콜라", 2000, 15, promotion);
        Product product4 = new Product("사이다", 3000, 20, null);

        Product purchased1 = new Product(product1, 5, Boolean.FALSE);
        Product purchased2 = new Product(product2, 6, Boolean.FALSE);
        Product purchased3 = new Product(product3, 7, Boolean.FALSE);
        Product purchased4 = new Product(product4, 8, Boolean.FALSE);

        return List.of(purchased1, purchased2, purchased3, purchased4);
    }
}
