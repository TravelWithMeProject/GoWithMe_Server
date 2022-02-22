package team.backend.goWithMe.domain.trip.dto.response;

import lombok.Getter;
import team.backend.goWithMe.global.error.exception.ErrorCode;

@Getter
public class TimeTableErrorResponseDTO {

    private final String message;

    private final int status;

    private final String code;

    public TimeTableErrorResponseDTO(String message, ErrorCode errorCode) {
        this.message = message;
        this.status = errorCode.status();
        this.code = errorCode.code();
    }
}