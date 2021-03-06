package team.backend.goWithMe.domain.trip.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Access(AccessType.FIELD)
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class TimeTableContent {

    @NotNull
    @Column(name = "content", columnDefinition = "TEXT")
    private String content; // "" is allowed.

    public static TimeTableContent from(String content) {
        return new TimeTableContent(content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.tableContent());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof TimeTableContent))
            return false;
        TimeTableContent timeTableContent = (TimeTableContent)o;
        return Objects.equals(this.tableContent(), timeTableContent.content);
    }

    @JsonValue
    public String tableContent() {
        return this.content;
    }

    public TimeTableContent resetContent() {
        return TimeTableContent.from("");
    }

//    public boolean isBlank() {
//        return this.content.length() == 0;
//    }


}
