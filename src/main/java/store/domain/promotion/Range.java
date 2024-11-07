package store.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

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