package store.domain;


import static camp.nextstep.edu.missionutils.DateTimes.now;

import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PromotionTest {

    @DisplayName("checkDate_메서드_테스트_01")
    @Test
    void checkData_기능_테스트() {
        LocalDate startTime = LocalDate.of(2024,10,1);
        LocalDate endTime_valid = LocalDate.of(2024,12,30);

        String promotionName = "2+1";
        BuyGet three_but_one_get = new BuyGet(3,1);
        Range validRange = new Range(startTime,endTime_valid);

        Promotion promotion = new Promotion(promotionName,three_but_one_get,validRange);
        Assertions.assertThat(promotion.checkDate(now()))
                .isTrue();
    }

    @DisplayName("getBuyCount_메서드_테스트_01")
    @Test
    void getBuyStock_기능_테스트() {
        LocalDate startTime = LocalDate.of(2024,10,1);
        LocalDate endTime_valid = LocalDate.of(2024,12,30);

        String promotionName = "2+1";
        BuyGet three_but_one_get = new BuyGet(3,1);
        Range validRange = new Range(startTime,endTime_valid);

        Promotion promotion = new Promotion(promotionName,three_but_one_get,validRange);

        Assertions.assertThat(promotion.getBuyStock(10))
                .isEqualTo(6);
    }
}
