package store.domain;


import static camp.nextstep.edu.missionutils.DateTimes.now;

import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.promotion.BuyGet;
import store.domain.promotion.Promotion;
import store.domain.promotion.Range;

public class PromotionTest {

    @DisplayName("checkDate_테스트_01")
    @Test
    void checkData_기능_테스트() {
        Promotion promotion = getTestPromotion();
        Assertions.assertThat(promotion.checkDate(now()))
                .isTrue();
    }

    @DisplayName("getTotalBuyStock_테스트_01")
    @Test
    void getTotalBuyStock_기능_테스트() {
        Promotion promotion = getTestPromotion();

        Assertions.assertThat(promotion.getTotalBuyStock(2,10))
                .isEqualTo(0);
    }

    @DisplayName("calculateFreeStock_테스트_01")
    @Test
    void calculateFreeStock_기능_테스트() {
        Promotion promotion = getTestPromotion();

        Assertions.assertThat(promotion.calculateFreeStock(12))
                .isEqualTo(4);
    }

    private Promotion getTestPromotion() {
        LocalDate startTime = LocalDate.of(2024,10,1);
        LocalDate endTime_valid = LocalDate.of(2024,12,30);

        String promotionName = "2+1";
        BuyGet three_but_one_get = new BuyGet(3,1);
        Range validRange = new Range(startTime,endTime_valid);

        return new Promotion(promotionName,three_but_one_get,validRange);
    }
}
