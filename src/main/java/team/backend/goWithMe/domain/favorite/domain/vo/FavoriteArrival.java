package team.backend.goWithMe.domain.favorite.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FavoriteArrival {

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
}
