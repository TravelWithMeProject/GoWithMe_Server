package team.backend.goWithMe.domain.favorite.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import team.backend.goWithMe.domain.favorite.domain.persist.Favorite;
import team.backend.goWithMe.domain.favorite.domain.vo.Accommodation;
import team.backend.goWithMe.domain.favorite.domain.vo.FavoriteArrival;
import team.backend.goWithMe.domain.favorite.domain.vo.FavoritePeriod;

import java.time.LocalDate;

@Getter
@JsonTypeName("favorite")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class FavoriteUpdateDTO {

    @JsonProperty("arrival")
    private FavoriteArrival favoriteArrival;

    @JsonProperty("accommodation")
    private Accommodation accommodation;

    @JsonProperty("startTime")
    private LocalDate startTime;

    @JsonProperty("endTime")
    private LocalDate endTime;

    public Favorite toEntity() {
        FavoritePeriod favoritePeriod = FavoritePeriod.of(startTime, endTime);
        return Favorite.createFavorite(favoriteArrival, accommodation, favoritePeriod);
    }
}
