package team.backend.goWithMe.domain.trip.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.domain.trip.exception.TimeTablePeriodValidException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;

import static team.backend.goWithMe.global.error.exception.ErrorCode.INVALID_INPUT_VALUE;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeTablePeriod implements IPeriod {

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

    @Override
    public LocalDateTime getStart() {
        return totalStart;
    }

    @Override
    public LocalDateTime getEnd() {
        return totalEnd;
    }

    @Override
    public Duration ofDuration() {
        return Duration.between(totalStart, totalEnd);
    }

    @Override
    public void changePeriod(LocalDateTime start, LocalDateTime end) {
        validateTotalPeriod(start, end);
        this.totalStart = start;
        this.totalEnd = end;
    }

    private static void validateTotalPeriod(LocalDateTime totalStart, LocalDateTime totalEnd) {
        if (totalStart.isAfter(totalEnd)) {
            throw new TimeTablePeriodValidException("전체일정 시작 시점과 끝 시점이 반대입니다.", INVALID_INPUT_VALUE);
        }
        if (totalStart.isEqual(totalEnd)) {
            throw new TimeTablePeriodValidException("전체일정이 0(끝지점과 시작지점이 같음)이 될 수 없습니다.", INVALID_INPUT_VALUE);
        }
    }
}
