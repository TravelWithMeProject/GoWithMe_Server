package team.backend.goWithMe.domain.trip.exception;

import team.backend.goWithMe.global.error.exception.ErrorCode;
import team.backend.goWithMe.global.error.exception.InvalidValueException;

public class SchedulePeriodInvalidException extends InvalidValueException {
    public SchedulePeriodInvalidException(String value) {
        super(value);
    }

    public SchedulePeriodInvalidException(String value, ErrorCode errorCode) {
        super(value, errorCode);
    }
}
