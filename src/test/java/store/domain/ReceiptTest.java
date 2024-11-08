package store.domain;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.promotion.Promotion;

public class ReceiptTest {

    @DisplayName("canUseMembership_테스트_01")
    @Test
    void canUseMembership_기능_테스트() {
        List<Product> products = getProducts();

        Receipt receipt = new Receipt(products,true);

        Assertions.assertThat(receipt.canUseMemberShip())
                .isTrue();
    }

    @DisplayName("금액_계산_테스트_01")
    @Test
    void calculatePrice_기능_테스트() {
        List<Product> products = getProducts();

        Receipt receipt = new Receipt(products,true);
        receipt.calculatePrice();

        Assertions.assertThat(receipt.getTotalPrice())
                .isEqualTo(69000);
        Assertions.assertThat(receipt.getPromotionPrice())
                .isEqualTo(25000);
    }

    @DisplayName("멤버쉽_적용_테스트_01")
    @Test
    void applyMembership_기능_테스트() {
        List<Product> products = getProducts();

        Receipt receipt = new Receipt(products,true);
        receipt.calculatePrice();
        receipt.applyMembership();

        Assertions.assertThat(receipt.getMembershipPrice())
                .isEqualTo(8000);
    }

    private List<Product> getProducts() {
        Promotion promotion = new Promotion("test", null, null);

        Product product1 = new Product("치킨", 5000, 10, promotion);
        Product product2 = new Product("치킨", 1000, 10, null);
        Product product3 = new Product("콜라", 2000, 15, promotion);
        Product product4 = new Product("사이다", 3000, 20, null);

        Product purchased1 = new Product(product1, 5, Boolean.TRUE);
        Product purchased2 = new Product(product2, 6, Boolean.FALSE);
        Product purchased3 = new Product(product3, 7, Boolean.FALSE);
        Product purchased4 = new Product(product4, 8, Boolean.FALSE);

        return List.of(purchased1, purchased2, purchased3, purchased4);
    }
}
