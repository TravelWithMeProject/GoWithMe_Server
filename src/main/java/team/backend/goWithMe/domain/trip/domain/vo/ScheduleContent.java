package team.backend.goWithMe.domain.trip.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.util.Objects;

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

    @Override
    public int hashCode() {
        return Objects.hash(scheduleContent());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ScheduleContent))
            return false;
        ScheduleContent content = (ScheduleContent)o;
        return Objects.equals(scheduleContent(), content.scheduleContent);
    }

    public ScheduleContent clearScheduleContent() {
        return new ScheduleContent("");
    }

}
