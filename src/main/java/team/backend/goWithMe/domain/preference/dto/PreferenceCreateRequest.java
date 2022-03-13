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
import team.backend.goWithMe.domain.preference.domain.vo.PreferenceArrival;
import team.backend.goWithMe.domain.preference.domain.vo.PreferencePeriod;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonTypeName("prefer")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class PreferenceCreateRequest {

    @JsonProperty("arrival")
    private PreferenceArrival favoriteArrival;

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

    private PreferencePeriod createPeriod() {
        return PreferencePeriod.of(startTime, endTime);
    }

    public static PreferenceCreateRequest createRequest(final Preference preference) {
        return new PreferenceCreateRequest(preference.getPreferenceArrival(), preference.getAccommodation(),
                preference.getPreferencePeriod().startTime(), preference.getPreferencePeriod().endTime());
    }

    public Preference toEntity() {
        return Preference.createSurvey(favoriteArrival, accommodation, createPeriod());
    }
}
