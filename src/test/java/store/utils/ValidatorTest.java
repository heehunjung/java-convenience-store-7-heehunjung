package store.utils;

import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ValidatorTest {

    @DisplayName("productAndStockFormatValidator_테스트_01")
    @Test
    void 괄호_상품명_언더바_숫자_괄호_패턴이_아닌_경우_예외를_발생한다() {
        String validInput = "[사이다-22]";
        String invalidInput = "[sidar-2]";

        Assertions.assertThatNoException()
                .isThrownBy(() -> Validator.purchaseInputFormatValidator(validInput));
        Assertions.assertThatThrownBy(() -> Validator.productAndStockFormatValidator(invalidInput))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("purchaseInputFormatValidator_테스트_01")
    @Test
    void 구매할_수량과_수량_형식이_올바르지_않은_경우_예외를_발생한다() {
        String validInput = "[사이다-2],[감자칩-1]";
        String invalidInput = " [사이다-2].[감자칩-1]";

        Assertions.assertThatNoException()
                .isThrownBy(() -> Validator.purchaseInputFormatValidator(validInput));
        Assertions.assertThatThrownBy(() -> Validator.purchaseInputFormatValidator(invalidInput))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("YesOrNoValidator_테스트_01")
    @Test
    void Y_N를_제외한_입력이면_예외를_발생한다() {
        String validInput = "Y";
        String invalidInput = "M";

        Assertions.assertThatNoException()
                .isThrownBy(() -> Validator.YesOrNoValidator(validInput));
        Assertions.assertThatThrownBy(() -> Validator.YesOrNoValidator(invalidInput))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("nullOrEmptyValidator_테스트_01")
    @Test
    void null값_또는_빈값이_들어오면_예외를_발생한다() {
        String nullInput = null;
        String emptyInput = "  ";

        Assertions.assertThatThrownBy(() -> Validator.nullOrEmptyValidator(nullInput))
                .isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> Validator.nullOrEmptyValidator(emptyInput))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("duplicatedNameValidator_테스트_01")
    @Test
    void 중복된_이름이_입력되면_예외를_발생한다() {
        List<String> validInput = Arrays.asList("치킨","피자","콜라");
        List<String> invalidInput = Arrays.asList("치킨","치킨","콜라");

        Assertions.assertThatNoException()
                .isThrownBy(() -> Validator.duplicatedNameValidator(validInput));
        Assertions.assertThatThrownBy(() -> Validator.duplicatedNameValidator(invalidInput))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
