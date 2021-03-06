package team.backend.goWithMe.domain.trip.dto.request;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.domain.member.domain.persist.Member;
import team.backend.goWithMe.domain.trip.domain.persist.TimeTable;
import team.backend.goWithMe.domain.trip.domain.vo.TimeTableContent;
import team.backend.goWithMe.domain.trip.domain.vo.TimeTableName;
import team.backend.goWithMe.domain.trip.domain.vo.TimeTablePeriod;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@ApiModel
@JsonTypeName("timeTable")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TimeTableCreateDTO {

    @ApiModelProperty(value = "Member 아이디 값", required = true, example = "1")
    @NotNull
    private Long userId;

    @ApiModelProperty(value = "시간표 명", required = true, example = "몽골 온천 여행")
    @NotBlank
    private String timeTableName;

    @ApiModelProperty(value = "시간표 내용", required = false, example = "이번 온천 여행은 몽골로 가보기로 했다.(Null은 안됨)")
    @NotNull
    private String timeTableContent;

    @ApiModelProperty(value = "여행 기간 시작 시점", required = true, example = "2022-01-01T00:00:00")
    private LocalDateTime totalPeriodStart;

    @ApiModelProperty(value = "여행 기간 끝 시점", required = true, example = "2022-01-21T13:00:00")
    private LocalDateTime totalPeriodEnd;

    public TimeTable toEntity(Member member) {
        return TimeTable.createTimeTable(TimeTableName.from(timeTableName),
                TimeTableContent.from(timeTableContent),
                TimeTablePeriod.between(totalPeriodStart, totalPeriodEnd), member);
    }
}