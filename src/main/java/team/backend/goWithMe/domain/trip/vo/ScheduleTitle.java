package team.backend.goWithMe.domain.trip.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.domain.trip.exception.ScheduleTitleInvalidException;
import team.backend.goWithMe.global.error.exception.InvalidValueException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ScheduleTitle {

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

    private static void validateScheduleTitle(String scheduleTitle) {
        if (scheduleTitle == null || scheduleTitle.isBlank()) {
            throw new ScheduleTitleInvalidException("세부 스케줄 이름은 필수입니다.");
        }
    }

}
