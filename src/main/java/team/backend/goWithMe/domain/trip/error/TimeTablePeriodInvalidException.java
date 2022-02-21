package team.backend.goWithMe.domain.trip.error;

import team.backend.goWithMe.global.error.exception.ErrorCode;
import team.backend.goWithMe.global.error.exception.InvalidValueException;

public class TimeTablePeriodInvalidException extends InvalidValueException {
    public TimeTablePeriodInvalidException(ErrorCode errorCode) {
        super(errorCode);
    }
}
