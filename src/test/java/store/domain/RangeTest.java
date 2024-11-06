package store.domain;

import static camp.nextstep.edu.missionutils.DateTimes.now;

import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RangeTest {

    @DisplayName("isValidRange_테스트_01")
    @Test
    void isValidRange_기능_테스트() {
        LocalDate startTime = LocalDate.of(2024,10,1);
        LocalDate endTime = LocalDate.of(2024,10,10);
        LocalDate endTime_valid = LocalDate.of(2024,12,30);

        Range invalidRange = new Range(startTime,endTime);
        Range validRange = new Range(startTime,endTime_valid);

        Assertions.assertThat(invalidRange.isValidRange(now()))
                .isFalse();
        Assertions.assertThat(validRange.isValidRange(now()))
                .isTrue();
    }
}
