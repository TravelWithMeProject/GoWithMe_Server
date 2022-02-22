package team.backend.goWithMe.domain.trip.error.exception;

import team.backend.goWithMe.global.error.exception.BusinessException;
import team.backend.goWithMe.global.error.exception.ErrorCode;

public class TimeTableNameInvalidException extends BusinessException {
    public TimeTableNameInvalidException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public TimeTableNameInvalidException(ErrorCode errorCode) {
        super(errorCode);
    }
}
