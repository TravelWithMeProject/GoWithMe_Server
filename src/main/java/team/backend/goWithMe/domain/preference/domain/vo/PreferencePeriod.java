package team.backend.goWithMe.domain.preference.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.domain.preference.error.OverPeriodException;
import team.backend.goWithMe.global.error.exception.ErrorCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class PreferencePeriod {

    @Column(name = "start_time", nullable = false)
    private LocalDate startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDate endTime;

    public static PreferencePeriod of(final LocalDate startTime, final LocalDate endTime) {
        if (overPeriod(startTime, endTime)) {
            throw new OverPeriodException(ErrorCode.OVER_PERIOD_ERROR);
        }

        return new PreferencePeriod(startTime, endTime);
    }

    private static boolean overPeriod(final LocalDate startTime, final LocalDate endTime) {
        return startTime.isAfter(endTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreferencePeriod time = (PreferencePeriod) o;
        return Objects.equals(startTime(), time.startTime) && Objects.equals(endTime(), time.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime(), endTime());
    }

    @JsonValue
    public LocalDate startTime() {
        return startTime;
    }

    @JsonValue
    public LocalDate endTime() {
        return endTime;
    }
}
