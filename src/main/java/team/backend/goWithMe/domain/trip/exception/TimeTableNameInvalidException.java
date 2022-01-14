package team.backend.goWithMe.domain.trip.exception;

import team.backend.goWithMe.global.error.exception.ErrorCode;
import team.backend.goWithMe.global.error.exception.InvalidValueException;

public class TimeTableNameInvalidException extends InvalidValueException {
    public TimeTableNameInvalidException(String value) {
        super(value);
    }

    public TimeTableNameInvalidException(String value, ErrorCode errorCode) {
        super(value, errorCode);
    }
}
