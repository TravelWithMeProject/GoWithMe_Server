package team.backend.goWithMe.domain.preference.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.domain.preference.domain.persist.Preference;
import team.backend.goWithMe.domain.preference.domain.vo.Accommodation;
import team.backend.goWithMe.domain.preference.domain.vo.PreferenceArrival;
import team.backend.goWithMe.domain.preference.domain.vo.PreferencePeriod;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonTypeName("prefer")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class PreferenceUpdateDTO {

    @JsonProperty("arrival")
    private PreferenceArrival favoriteArrival;

    @JsonProperty("accommodation")
    private Accommodation accommodation;

    @JsonProperty("startTime")
    private LocalDate startTime;

    @JsonProperty("endTime")
    private LocalDate endTime;

    public static PreferenceUpdateDTO createUpdate(final Preference preference) {
        return new PreferenceUpdateDTO(preference.getPreferenceArrival(), preference.getAccommodation(),
                preference.getPreferencePeriod().startTime(), preference.getPreferencePeriod().endTime());
    }

    public Preference toEntity() {
        PreferencePeriod favoritePeriod = PreferencePeriod.of(startTime, endTime);
        return Preference.createSurvey(favoriteArrival, accommodation, favoritePeriod);
    }
}
