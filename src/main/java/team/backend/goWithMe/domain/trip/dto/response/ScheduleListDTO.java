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
@JsonTypeName("schedule")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@ApiModel
public class ScheduleListDTO {

    @ApiModelProperty(
            value = "스케줄(세부일정) 리스트",
            required = true,
            example = "[ { 세부일정1 정보 }, { 세부일정2 정보 }, ... ]")
    private final List<ScheduleDTO> timeTableList = new ArrayList<>();

    public void addSchedule(ScheduleDTO... scheduleDTO) {
        timeTableList.addAll(Arrays.asList(scheduleDTO));
    }
}
