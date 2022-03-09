package team.backend.goWithMe.domain.postcontent.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class Content {

    @NotBlank(message = "null 혹은 null string은 불가합니다.")
    @Column(columnDefinition = "TEXT")
    private String content;

    public static Content from(final String content) {
        return new Content(content);
    }

    @JsonValue
    public String content() {
        return content;
    }

    @Override
    public boolean equals(Object obj) {

        if(this == obj) {
            return true;
        }

        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Content content = (Content) obj;
        return Objects.equals(content(), content.content());
    }

    @Override
    public int hashCode() {
        return Objects.hash(content());
    }
}
