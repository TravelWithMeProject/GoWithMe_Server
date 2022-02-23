package team.backend.goWithMe.domain.preference.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.domain.preference.domain.persist.Preference;
import team.backend.goWithMe.domain.preference.domain.vo.Accommodation;
import team.backend.goWithMe.domain.preference.domain.vo.FavoriteArrival;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonTypeName("favorite")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PreferenceCreateResponse {

    @JsonProperty("arrival")
    private FavoriteArrival favoriteArrival;

    @JsonProperty("accommodation")
    private Accommodation accommodation;

    @JsonProperty("startTime")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy")
    private LocalDate startTime;

    @JsonProperty("endTime")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy")
    private LocalDate endTime;

    public static PreferenceCreateResponse createResponse(final Preference favorite) {

        LocalDate startTime = favorite.getFavoritePeriod().startTime();
        LocalDate endTime = favorite.getFavoritePeriod().endTime();

        return new PreferenceCreateResponse(favorite.getFavoriteArrival(), favorite.getAccommodation(),
                startTime, endTime);
    }
}
