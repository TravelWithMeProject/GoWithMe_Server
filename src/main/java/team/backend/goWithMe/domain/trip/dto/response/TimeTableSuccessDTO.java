package team.backend.goWithMe.domain.trip.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ApiModel
public class TimeTableSuccessDTO {
    @ApiModelProperty(value = "성공 여부", required = true, example = "true")
    private final boolean success;
}