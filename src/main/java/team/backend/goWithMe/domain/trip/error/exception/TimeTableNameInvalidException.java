package team.backend.goWithMe.domain.trip.error.exception;

import team.backend.goWithMe.domain.trip.error.TimeTableErrorCode;
import team.backend.goWithMe.global.error.exception.CommonErrorCode;
import team.backend.goWithMe.global.error.exception.InvalidValueException;

public class TimeTableNameInvalidException extends TimeTableBusinessException {
    public TimeTableNameInvalidException(String message, TimeTableErrorCode errorCode) {
        super(message, errorCode);
    }

    public TimeTableNameInvalidException(TimeTableErrorCode errorCode) {
        super(errorCode);
    }
}
