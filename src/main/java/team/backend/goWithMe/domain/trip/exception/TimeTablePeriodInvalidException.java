package team.backend.goWithMe.domain.trip.exception;

import team.backend.goWithMe.global.error.exception.CommonErrorCode;
import team.backend.goWithMe.global.error.exception.ErrorCode;
import team.backend.goWithMe.global.error.exception.InvalidValueException;

public class TimeTablePeriodInvalidException extends InvalidValueException {
    public TimeTablePeriodInvalidException(String value) {
        super(value);
    }

    public TimeTablePeriodInvalidException(String value, CommonErrorCode errorCode) {
        super(value, errorCode);
    }
}
