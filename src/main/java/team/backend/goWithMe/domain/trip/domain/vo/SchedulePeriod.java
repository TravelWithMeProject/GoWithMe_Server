package team.backend.goWithMe.domain.trip.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.domain.trip.error.SchedulePeriodInvalidException;
import team.backend.goWithMe.global.error.exception.ErrorCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class SchedulePeriod{

    @NotNull
    @Column(name = "detail_start")
    private LocalDateTime detailStart;

    @NotNull
    @Column(name = "detail_end")
    private LocalDateTime detailEnd;

    @JsonValue
    public LocalDateTime scheduleDetailStart() {
        return this.detailStart;
    }

    @JsonValue
    public LocalDateTime scheduleDetailEnd() {
        return this.detailEnd;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.scheduleDetailStart(), this.scheduleDetailEnd());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof SchedulePeriod))
            return false;
        SchedulePeriod period = (SchedulePeriod)o;
        return Objects.equals(this.scheduleDetailStart(), period.detailStart) &&
                Objects.equals(this.scheduleDetailEnd(), period.detailEnd);
    }

    public static SchedulePeriod between(LocalDateTime detailStart, LocalDateTime detailEnd, TimeTablePeriod totalPeriod) {
        validateDetailPeriod(detailStart, detailEnd, totalPeriod);
        return new SchedulePeriod(detailStart, detailEnd);
    }

    public Duration ofDuration() {
        return Duration.between(this.detailStart, this.detailEnd);
    }

    public SchedulePeriod changePeriod(SchedulePeriod period, TimeTablePeriod totalPeriod) {
        validateDetailPeriod(period.scheduleDetailStart(), period.scheduleDetailEnd(), totalPeriod);
        return new SchedulePeriod(period.scheduleDetailStart(), period.scheduleDetailEnd());
    }

    private static void validateDetailPeriod(LocalDateTime detailStart, LocalDateTime detailEnd, TimeTablePeriod totalPeriod) {
        validateDetailPeriodInTotalPeriod(detailStart, totalPeriod);
        validateDetailPeriodInTotalPeriod(detailEnd, totalPeriod);
        if (detailStart.isAfter(detailEnd)) {
            throw new SchedulePeriodInvalidException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        if (detailEnd.isBefore(detailStart)) {
            throw new SchedulePeriodInvalidException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private static void validateDetailPeriodInTotalPeriod(LocalDateTime detailOne, TimeTablePeriod totalPeriod) {
        if (detailOne.isBefore(totalPeriod.totalPeriodStart())) {
            throw new SchedulePeriodInvalidException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        if (detailOne.isAfter(totalPeriod.totalPeriodEnd())) {
            throw new SchedulePeriodInvalidException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
