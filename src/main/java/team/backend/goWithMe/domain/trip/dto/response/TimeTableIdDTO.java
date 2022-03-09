package team.backend.goWithMe.domain.trip.dto.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonTypeName("timeTable")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@AllArgsConstructor
@ApiModel
public class TimeTableIdDTO {

    @ApiModelProperty(value = "성공 여부", required = true, example = "false")
    private boolean success;

    @ApiModelProperty(value = "시간표 아이디 값", required = true, example = "1")
    private long timeTableId;

}