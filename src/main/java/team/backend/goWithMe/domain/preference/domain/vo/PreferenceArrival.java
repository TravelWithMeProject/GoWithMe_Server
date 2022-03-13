package team.backend.goWithMe.domain.preference.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class PreferenceArrival {

    @NotNull(message = "null은 안됩니다.")
    @Column(name = "preference_arrival", nullable = false, length = 50)
    private String preferenceArrival;

    public static PreferenceArrival from(final String favoriteArrival) {
        return new PreferenceArrival(favoriteArrival);
    }

    @JsonValue
    public String preferenceArrival() {
        return preferenceArrival;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreferenceArrival favoriteArrival = (PreferenceArrival) o;
        return Objects.equals(preferenceArrival(), favoriteArrival.preferenceArrival());
    }

    @Override
    public int hashCode() {
        return Objects.hash(preferenceArrival());
    }
}
