package team.backend.goWithMe.domain.trip.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ApiModel
public class TimeTableIdDTO {

    @ApiModelProperty(value = "성공 여부", required = true, example = "false")
    private boolean success;

    @ApiModelProperty(value = "시간표 아이디 값", required = true, example = "1")
    private long timeTableId;



}