package team.backend.goWithMe.domain.preference.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.domain.preference.domain.persist.Preference;
import team.backend.goWithMe.domain.preference.domain.vo.Accommodation;
import team.backend.goWithMe.domain.preference.domain.vo.FavoriteArrival;
import team.backend.goWithMe.domain.preference.domain.vo.FavoritePeriod;
import team.backend.goWithMe.global.common.AccessToken;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonTypeName("prefer")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class PreferenceCreateRequest {

    @JsonProperty("arrival")
    private FavoriteArrival favoriteArrival;

    @JsonProperty("accommodation")
    private Accommodation accommodation;

    @JsonProperty("start_time")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy")
    private LocalDate startTime;

    @JsonProperty("end_time")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy")
    private LocalDate endTime;

    private FavoritePeriod createPeriod() {
        return FavoritePeriod.of(startTime, endTime);
    }

    public Preference toEntity() {
        return Preference.createSurvey(favoriteArrival, accommodation, createPeriod());
    }
}
