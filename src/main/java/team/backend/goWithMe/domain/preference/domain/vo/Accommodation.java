package team.backend.goWithMe.domain.preference.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public final class Accommodation {

    @NotNull(message = "null은 안됩니다.")
    @Column(nullable = false, length = 50)
    private String accommodation;

    public static Accommodation from(final String accommodation) {
        return new Accommodation(accommodation);
    }

    @JsonValue
    public String accommodation() {
        return accommodation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Accommodation accommodation = (Accommodation) o;
        return Objects.equals(accommodation(),accommodation.accommodation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accommodation());
    }
}
