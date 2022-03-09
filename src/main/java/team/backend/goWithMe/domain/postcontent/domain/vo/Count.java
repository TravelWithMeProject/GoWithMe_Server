package team.backend.goWithMe.domain.postcontent.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class Count {

    private Long count;

    public static Count from(final Long count) {
        return new Count(count);
    }

    @JsonValue
    public Long count() {
        return count;
    }

    @Override
    public boolean equals(Object obj) {

        if(this == obj) {
            return true;
        }

        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Count count = (Count) obj;
        return Objects.equals(count(), count.count());
    }

    @Override
    public int hashCode() {
        return Objects.hash(count());
    }
}
