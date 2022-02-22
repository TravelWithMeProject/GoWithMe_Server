package team.backend.goWithMe.domain.trip.dto.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonTypeName("schedule")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@AllArgsConstructor
@ApiModel
public class ScheduleDTO {

    @ApiModelProperty(example = "1")
    private long scheduleId;

    @ApiModelProperty(value = "세부일정 명", required = true, example = "몽골 수도 맛집 XXX 방문")
    private String scheduleTitle;

    @ApiModelProperty(value = "세부일정 내용", required = false, example = "시장 들렀다가 XXX라는 맛집 방문 예정")
    private String scheduleContent;

    @ApiModelProperty(value = "세부일정 기간 시작 시점", required = true, example = "2022-01-02T15:00:00")
    private LocalDateTime totalPeriodStart;

    @ApiModelProperty(value = "세부일정 기간 끝 시점", required = true, example = "2022-01-02T16:00:00")
    private LocalDateTime totalPeriodEnd;

    @ApiModelProperty(value = "세부일정 비용", required = true, example = "10000")
    private long cost;

}
