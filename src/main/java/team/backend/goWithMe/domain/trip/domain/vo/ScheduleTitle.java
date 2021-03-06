package team.backend.goWithMe.domain.trip.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.domain.trip.error.exception.ScheduleTitleInvalidException;
import team.backend.goWithMe.global.error.exception.ErrorCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ScheduleTitle {

    @NotBlank
    @Column(name = "schedule_title", length = 50) // 한글 기준 25자 제한
    private String title;

    public static ScheduleTitle from(String scheduleTitle) {
        validateScheduleTitle(scheduleTitle);
        return new ScheduleTitle(scheduleTitle);
    }

    @JsonValue
    public String scheduleTitle() {
        return this.title;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.scheduleTitle());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ScheduleTitle))
            return false;
        ScheduleTitle title = (ScheduleTitle)o;
        return Objects.equals(this.scheduleTitle(), title.title);
    }

    private static void validateScheduleTitle(String scheduleTitle) {
        if (scheduleTitle == null || scheduleTitle.isBlank()) {
            throw new ScheduleTitleInvalidException(ErrorCode.INVALID_TITLE_OR_NAME);
        }
    }

}
