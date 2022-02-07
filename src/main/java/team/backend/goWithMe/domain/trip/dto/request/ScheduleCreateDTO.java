package team.backend.goWithMe.domain.trip.dto.request;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import team.backend.goWithMe.domain.trip.domain.persist.Schedule;
import team.backend.goWithMe.domain.trip.domain.persist.TimeTable;
import team.backend.goWithMe.domain.trip.domain.vo.ScheduleContent;
import team.backend.goWithMe.domain.trip.domain.vo.ScheduleCost;
import team.backend.goWithMe.domain.trip.domain.vo.SchedulePeriod;
import team.backend.goWithMe.domain.trip.domain.vo.ScheduleTitle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@ApiModel
@JsonTypeName("schedule")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ScheduleCreateDTO {

    @ApiModelProperty(value = "TimeTable 아이디 값", required = true, example = "1")
    @NotNull
    private Long timeTableId;

    @ApiModelProperty(value = "세부일정 이름", required = true, example = "몽골 수도 맛집 XXX 방문")
    @NotBlank
    private String scheduleTitle;

    @ApiModelProperty(value = "세부일정 내용", required = false, example = "시장 들렀다가 XXX라는 맛집 방문 예정")
    private String scheduleContent;

    @ApiModelProperty(value = "세부일정 시작 시점", required = true, example = "2022-01-02T15:00:00")
    private LocalDateTime detailPeriodStart;

    @ApiModelProperty(value = "세부일정 끝 시점", required = true, example = "2022-01-02T16:30:00")
    @NotBlank
    private LocalDateTime detailPeriodEnd;

    @ApiModelProperty(value = "세부일정 비용", required = true, example = "10000")
    @Range(min = 0L, max = 99999999L)
    private long cost;

    public Schedule toEntity(TimeTable timeTable) {
        return Schedule.createSchedule(
                timeTable,
                ScheduleTitle.from(scheduleTitle),
                ScheduleContent.from(scheduleContent),
                SchedulePeriod.between(detailPeriodStart, detailPeriodEnd, timeTable.getTotalPeriod()),
                ScheduleCost.wons(cost)
        );
    }

}
