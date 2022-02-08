package team.backend.goWithMe.domain.postcontent.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.*;
import java.util.Objects;

@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class Title {

    @NotBlank(message = "null 혹은 null string은 불가합니다.")
    @Size(min = 10, max = 50)
    private String title;

    public static Title from(final String title) {
        return new Title(title);
    }

    @JsonValue
    public String title() {
        return title;
    }

    @Override
    public boolean equals(Object obj) {

        if(this == obj) {
            return true;
        }

        if(obj == null || getClass() != obj.getClass()){
            return false;
        }

        Title title = (Title) obj;
        return Objects.equals(title(), title.title());
    }

    @Override
    public int hashCode() {
        return Objects.hash(title());
    }
}
