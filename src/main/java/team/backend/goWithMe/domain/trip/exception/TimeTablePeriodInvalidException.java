package team.backend.goWithMe.domain.trip.exception;

import team.backend.goWithMe.global.error.exception.ErrorCode;
import team.backend.goWithMe.global.error.exception.InvalidValueException;

public class TimeTablePeriodInvalidException extends InvalidValueException {
    public TimeTablePeriodInvalidException(String value) {
        super(value);
    }

    public TimeTablePeriodInvalidException(String value, ErrorCode errorCode) {
        super(value, errorCode);
    }
}
