package team.backend.goWithMe.domain.favorite.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.tomcat.jni.Local;
import team.backend.goWithMe.domain.favorite.domain.persist.Favorite;
import team.backend.goWithMe.domain.favorite.domain.vo.Accommodation;
import team.backend.goWithMe.domain.favorite.domain.vo.FavoriteArrival;
import team.backend.goWithMe.domain.favorite.domain.vo.FavoritePeriod;

import java.time.LocalDate;

@Getter
@JsonTypeName("favorite")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FavoriteCreateResponse {

    @JsonProperty("arrival")
    private FavoriteArrival favoriteArrival;

    @JsonProperty("accommodation")
    private Accommodation accommodation;

    @JsonProperty("startTime")
    private LocalDate startTime;

    @JsonProperty("endTime")
    private LocalDate endTime;

    public static FavoriteCreateResponse createResponse(final Favorite favorite) {

        LocalDate startTime = favorite.getFavoritePeriod().startTime();
        LocalDate endTime = favorite.getFavoritePeriod().endTime();

        return new FavoriteCreateResponse(favorite.getFavoriteArrival(), favorite.getAccommodation(),
                startTime, endTime);
    }
}
