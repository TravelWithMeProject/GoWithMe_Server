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
@JsonTypeName("timeTable")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@AllArgsConstructor
@ApiModel
public class TimeTableDTO {

    @ApiModelProperty(example = "1")
    private long timeTableId;

    @ApiModelProperty(value = "시간표 명", required = true, example = "몽골 온천 여행")
    private String tableName;

    @ApiModelProperty(value = "시간표 내용", required = false, example = "이번 온천 여행은 몽골로 가보기로 했다.")
    private String content;

    @ApiModelProperty(value = "여행 기간 시작 시점", required = true, example = "2022-01-01T00:00:00")
    private LocalDateTime totalPeriodStart;

    @ApiModelProperty(value = "여행 기간 끝 시점", required = true, example = "2022-01-21T13:00:00")
    private LocalDateTime totalPeriodEnd;

}