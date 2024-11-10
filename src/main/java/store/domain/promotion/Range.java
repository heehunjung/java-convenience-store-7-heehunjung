package store.domain.promotion;

import java.time.LocalDate;

public class Range {
    LocalDate startTime;
    LocalDate endTime;

    public Range(LocalDate startTime, LocalDate endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean isValidRange(LocalDate now) {
        return !now.isBefore(startTime) && !now.isAfter(endTime);
    }

}
