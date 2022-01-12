package team.backend.goWithMe.domain.trip.exception;

import team.backend.goWithMe.global.error.exception.ErrorCode;
import team.backend.goWithMe.global.error.exception.InvalidValueException;

public class TimeTablePeriodValidException extends InvalidValueException {
    public TimeTablePeriodValidException(String value) {
        super(value);
    }

    public TimeTablePeriodValidException(String value, ErrorCode errorCode) {
        super(value, errorCode);
    }
}
