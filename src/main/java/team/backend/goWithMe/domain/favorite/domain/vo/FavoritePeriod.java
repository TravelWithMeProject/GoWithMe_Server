package team.backend.goWithMe.domain.favorite.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.domain.favorite.domain.error.OverPeriodException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class FavoritePeriod {

    @Column(name = "start_time", nullable = false)
    private LocalDate startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDate endTime;

    public static FavoritePeriod of(final LocalDate startTime, final LocalDate endTime) {
        if (overPeriod(startTime, endTime)) {
            throw new OverPeriodException("시작 날짜가 마지막 날짜 보다 앞서있습니다.");
        }

        return new FavoritePeriod(startTime, endTime);
    }

    private static boolean overPeriod(final LocalDate startTime, final LocalDate endTime) {
        return startTime.isAfter(endTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoritePeriod time = (FavoritePeriod) o;
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
