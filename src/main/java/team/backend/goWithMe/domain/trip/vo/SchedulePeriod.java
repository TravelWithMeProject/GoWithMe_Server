package team.backend.goWithMe.domain.trip.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.domain.trip.exception.SchedulePeriodInvalidException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SchedulePeriod{

    @NotNull
    @Column(name = "detail_start")
    private LocalDateTime detailStart;

    @NotNull
    @Column(name = "detail_end")
    private LocalDateTime detailEnd;

    public LocalDateTime scheduleDetailStart() {
        return this.detailStart;
    }

    public LocalDateTime scheduleDetailEnd() {
        return this.detailEnd;
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
            throw new SchedulePeriodInvalidException("세부일정 시작 지점은 끝 시점보다 늦을 수 없습니다.");
        }
        if (detailEnd.isBefore(detailStart)) {
            throw new SchedulePeriodInvalidException("세부일정 끝 시점은 시작 시점보다 빠를 수 없습니다.");
        }
    }

    private static void validateDetailPeriodInTotalPeriod(LocalDateTime detailOne, TimeTablePeriod totalPeriod) {
        if (detailOne.isBefore(totalPeriod.totalPeriodStart())) {
            throw new SchedulePeriodInvalidException("세부일정은 전체일정보다 빠를 수 없습니다.");
        }
        if (detailOne.isAfter(totalPeriod.totalPeriodEnd())) {
            throw new SchedulePeriodInvalidException("세부일정은 전체일정보다 늦을 수 없습니다.");
        }
    }
}
