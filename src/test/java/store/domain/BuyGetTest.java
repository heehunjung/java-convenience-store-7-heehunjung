package store.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.promotion.BuyGet;

public class BuyGetTest {

    @DisplayName("calculateBuyStock_테스트_01")
    @Test
    void calculateBuyStock_기능_테스트() {
        BuyGet three_but_one_get = new BuyGet(3,1);

        Assertions.assertThat(three_but_one_get.calculateBuyStock(8,8))
                .isEqualTo(6);
    }

    @DisplayName("isPromotionAvailable_테스트_01")
    @Test
    void isPromotionAvailable_기능_테스트() {
        BuyGet three_but_one_get = new BuyGet(3,1);

        Assertions.assertThat(three_but_one_get.isPromotionAvailable(4))
                .isFalse();
    }
}
