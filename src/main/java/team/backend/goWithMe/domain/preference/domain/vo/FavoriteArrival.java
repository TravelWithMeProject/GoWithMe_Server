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
public final class FavoriteArrival {

    @NotNull(message = "null은 안됩니다.")
    @Column(name = "favorite_arrival", nullable = false, length = 50)
    private String favoriteArrival;

    public static FavoriteArrival from(final String favoriteArrival) {
        return new FavoriteArrival(favoriteArrival);
    }

    @JsonValue
    public String favoriteArrival() {
        return favoriteArrival;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoriteArrival favoriteArrival = (FavoriteArrival) o;
        return Objects.equals(favoriteArrival(), favoriteArrival.favoriteArrival());
    }

    @Override
    public int hashCode() {
        return Objects.hash(favoriteArrival());
    }
}
