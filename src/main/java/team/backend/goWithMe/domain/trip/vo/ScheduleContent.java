package team.backend.goWithMe.domain.trip.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ScheduleContent {

    @NotNull
    @Column(name = "schedule_content", columnDefinition = "TEXT")
    private String scheduleContent; // null isn't allowed, but "" is ok.

    public static ScheduleContent from(String scheduleContent) {
        return new ScheduleContent(scheduleContent);
    }

    @JsonValue
    public String scheduleContent() {
        return this.scheduleContent;
    }

    public ScheduleContent clearScheduleContent() {
        return new ScheduleContent("");
    }
}
