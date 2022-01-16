package team.backend.goWithMe.domain.trip.error;

import team.backend.goWithMe.global.error.exception.CommonErrorCode;
import team.backend.goWithMe.global.error.exception.InvalidValueException;

public class SchedulePeriodInvalidException extends InvalidValueException {
    public SchedulePeriodInvalidException(String value) {
        super(value);
    }

    public SchedulePeriodInvalidException(String value, CommonErrorCode errorCode) {
        super(value, errorCode);
    }
}