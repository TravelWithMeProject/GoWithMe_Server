package team.backend.goWithMe.domain.preference.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import team.backend.goWithMe.domain.preference.domain.persist.Preference;
import team.backend.goWithMe.domain.preference.domain.vo.Accommodation;
import team.backend.goWithMe.domain.preference.domain.vo.FavoriteArrival;
import team.backend.goWithMe.domain.preference.domain.vo.FavoritePeriod;

import java.time.LocalDate;

@Getter
@JsonTypeName("favorite")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class PreferenceUpdateDTO {

    @JsonProperty("arrival")
    private FavoriteArrival favoriteArrival;

    @JsonProperty("accommodation")
    private Accommodation accommodation;

    @JsonProperty("startTime")
    private LocalDate startTime;

    @JsonProperty("endTime")
    private LocalDate endTime;

    public Preference toEntity() {
        FavoritePeriod favoritePeriod = FavoritePeriod.of(startTime, endTime);
        return Preference.createSurvey(favoriteArrival, accommodation, favoritePeriod);
    }
}
