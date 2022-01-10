package team.backend.goWithMe.domain.trip.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Access(AccessType.FIELD)
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeTableContent {

    @NotNull
    @Column(name = "content", columnDefinition = "TEXT")
    private String content; // "" is allowed.

    public static TimeTableContent from(String content) {
        return new TimeTableContent(content);
    }

    @JsonValue
    public String tableContent() {
        return this.content;
    }

    public void resetContent() {
        this.content = "";
    }

    public boolean isBlank() {
        return this.content.length() == 0;
    }


}
