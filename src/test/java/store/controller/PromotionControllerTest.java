package store.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.promotion.Promotion;

public class PromotionControllerTest {

    @DisplayName("setPromotions_테스트_01")
    @Test
    void 기능_테스트() {
        String start = "name,buy,get,start_date,end_date";
        String info1 = "탄산2+1,2,1,2024-01-01,2024-12-31";
        String info2 = "MD추천상품,1,1,2024-01-01,2024-12-31";
        String info3 = "반짝할인,1,1,2024-11-01,2024-11-30";
        List<String> infos = Arrays.asList(start, info1, info2, info3);

        PromotionController promotionController = new PromotionController();
        List<Promotion> promotionList = promotionController.setPromotions(infos);

        Assertions.assertThat(promotionList.size()).isEqualTo(3);
        Assertions.assertThat(promotionList.getFirst()
                        .getName())
                .isEqualTo("탄산2+1");
    }
}
