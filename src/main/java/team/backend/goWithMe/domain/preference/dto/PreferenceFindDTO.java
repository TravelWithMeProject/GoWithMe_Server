package team.backend.goWithMe.domain.preference.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.domain.preference.domain.vo.Accommodation;
import team.backend.goWithMe.domain.preference.domain.vo.FavoriteArrival;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonTypeName("favorite")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class PreferenceFindDTO {

    @JsonProperty("arrival")
    private FavoriteArrival favoriteArrival;

    @JsonProperty("accommodation")
    private Accommodation accommodation;

    @JsonProperty("startTime")
    private LocalDate startTime;

    @JsonProperty("endTime")
    private LocalDate endTime;


}