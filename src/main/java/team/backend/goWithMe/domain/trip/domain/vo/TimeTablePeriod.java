package team.backend.goWithMe.domain.trip.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.domain.trip.error.exception.TimeTablePeriodInvalidException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

import static team.backend.goWithMe.global.error.exception.ErrorCode.INVALID_INPUT_VALUE;
import static team.backend.goWithMe.global.error.exception.ErrorCode.PERIOD_MISMATCH_ERROR;


@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class TimeTablePeriod{

    @NotNull
    @Column(name = "total_start")
    private LocalDateTime totalStart;

    @NotNull
    @Column(name = "total_end")
    private LocalDateTime totalEnd;

    public static TimeTablePeriod between(LocalDateTime totalStart, LocalDateTime totalEnd) {
        validateTotalPeriod(totalStart, totalEnd);
        return new TimeTablePeriod(totalStart, totalEnd);
    }

    @JsonValue
    public LocalDateTime totalPeriodStart() {
        return totalStart;
    }

    @JsonValue
    public LocalDateTime totalPeriodEnd() {
        return totalEnd;
    }

    public Duration ofDuration() {
        return Duration.between(totalStart, totalEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.totalPeriodStart(), this.totalPeriodEnd());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof TimeTablePeriod))
            return false;
        TimeTablePeriod timeTablePeriod = (TimeTablePeriod)o;
        return this.totalPeriodStart() == timeTablePeriod.totalStart &&
                this.totalPeriodEnd() == timeTablePeriod.totalEnd;
    }

    public void changePeriod(TimeTablePeriod period) {
        validateTotalPeriod(period.totalPeriodStart(), period.totalPeriodEnd());
        this.totalStart = period.totalPeriodStart();
        this.totalEnd = period.totalPeriodEnd();
    }

    private static void validateTotalPeriod(LocalDateTime totalStart, LocalDateTime totalEnd) {
        if (totalStart.isAfter(totalEnd)) {
            throw new TimeTablePeriodInvalidException(PERIOD_MISMATCH_ERROR);
        }
        if (totalStart.isEqual(totalEnd)) {
            throw new TimeTablePeriodInvalidException(PERIOD_MISMATCH_ERROR);
        }
    }
}
