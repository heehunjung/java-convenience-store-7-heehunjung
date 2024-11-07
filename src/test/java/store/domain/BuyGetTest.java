package store.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BuyGetTest {

    @DisplayName("calculateBuyStock_테스트_01")
    @Test
    void 기능_테스트() {
        BuyGet three_but_one_get = new BuyGet(3,1);

        Assertions.assertThat(three_but_one_get.calculateBuyStock(8))
                .isEqualTo(6);
    }
}
