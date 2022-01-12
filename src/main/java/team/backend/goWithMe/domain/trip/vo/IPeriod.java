package team.backend.goWithMe.domain.trip.vo;

import java.time.Duration;
import java.time.LocalDateTime;

public interface IPeriod {
    public abstract LocalDateTime getStart();

    public abstract LocalDateTime getEnd();

    public abstract Duration ofDuration();

//    public void changeTotalPeriodStart(LocalDateTime newTotalStart) {
//        validateTotalPeriod(newTotalStart, this.totalEnd);
//        this.totalStart = newTotalStart;
//    }
//
//    public void changeTotalPeriodEnd(LocalDateTime newTotalEnd) {
//        validateTotalPeriod(this.totalStart, newTotalEnd);
//        this.totalEnd = newTotalEnd;
//    }

    public abstract void changePeriod(LocalDateTime start, LocalDateTime end);
}
