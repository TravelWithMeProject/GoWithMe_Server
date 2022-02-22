package team.backend.goWithMe.domain.trip.dto.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
@JsonTypeName("timeTable")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@ApiModel
public class TimeTableListDTO {
    @ApiModelProperty(
            value = "시간표 리스트",
            required = true,
            example = "[ { 시간표1 정보 }, { 시간표2 정보 }, ... ]")
    private final List<TimeTableDTO> timeTableList = new ArrayList<>();

    public void addTimeTable(TimeTableDTO... timeTableDTO) {
        timeTableList.addAll(Arrays.asList(timeTableDTO));
    }
}