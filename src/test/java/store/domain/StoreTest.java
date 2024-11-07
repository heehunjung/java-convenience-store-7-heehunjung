package store.domain;

import java.time.LocalDate;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.promotion.BuyGet;
import store.domain.promotion.Promotion;
import store.domain.promotion.Range;

public class StoreTest {

    @DisplayName("find_메서드_테스트_01")
    @Test
    void find_메서드_기능_테스트() {
        Store store = createStoreWithTestProductsAndPromotions();

        Product product1 = store.findProduct("치킨");
        Product promotionProduct1 = store.findPromotionProduct("치킨");

        Assertions.assertThat(product1.getName()).isEqualTo("치킨");
        Assertions.assertThat(promotionProduct1.getName()).isEqualTo("치킨");
        Assertions.assertThat(promotionProduct1.getPromotion().getName()).isEqualTo("3+1");
    }

    private Store createStoreWithTestProductsAndPromotions() {
        LocalDate startTime = LocalDate.of(2024, 10, 1);
        LocalDate endTime = LocalDate.of(2024, 12, 30);

        Promotion threePlusOne = new Promotion("3+1", new BuyGet(3, 1), new Range(startTime, endTime));
        Promotion twoPlusOne = new Promotion("2+1", new BuyGet(2, 1), new Range(startTime, endTime));

        Product product1 = new Product("치킨", 5000, 5, threePlusOne);
        Product product2 = new Product("치킨", 1000, 10, null);
        Product product3 = new Product("콜라", 2000, 15, twoPlusOne);
        Product product4 = new Product("사이다", 3000, 20, null);

        List<Product> products = List.of(product1, product2, product3, product4);
        List<Promotion> promotions = List.of(threePlusOne, twoPlusOne);

        return new Store(products, promotions);
    }
}
