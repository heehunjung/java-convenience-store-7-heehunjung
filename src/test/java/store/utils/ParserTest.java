package store.utils;

import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ParserTest {

    @DisplayName("itemAndStockParser_메서드_테스트_01")
    @Test
    void 기능_테스트() {
        String input = "[콜라-15]";
        Map<String, Integer> expected = new HashMap<>();

        Parser.itemAndStockParser(input,expected);

        Assertions.assertThat(expected).hasSize(1);
        Assertions.assertThat(expected.get("콜라"))
                .isEqualTo(15);
    }

    @DisplayName("productStockParser_메서드_테스트_02")
    @Test
    void 수량에_문자가_포함되면_에러를_발생한다() {
        String input = "[콜라-15개]";
        Map<String, Integer> expected = new HashMap<>();

        Assertions.assertThatThrownBy(() -> Parser.itemAndStockParser(input, expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
