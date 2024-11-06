package store.utils;

import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ParserTest {

    @DisplayName("productStockParser_메서드_테스트_01")
    @Test
    void 상품명과_수량을_파싱해준다() {
        String input = "[콜라-15]";
        Map<String, Integer> expected = Map.of("콜라", 15);

        Assertions.assertThat(Parser.productStockParser(input))
                .isEqualTo(expected);
    }
}
