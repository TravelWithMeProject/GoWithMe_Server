package team.backend.goWithMe.domain.trip.error.exception;

import team.backend.goWithMe.global.error.exception.ErrorCode;
import team.backend.goWithMe.global.error.exception.InvalidValueException;

public class TimeTableNameInvalidException extends TimeTableBusinessException {
    public TimeTableNameInvalidException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public TimeTableNameInvalidException(ErrorCode errorCode) {
        super(errorCode);
    }
}
