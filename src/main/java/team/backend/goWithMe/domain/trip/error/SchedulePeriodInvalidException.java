package team.backend.goWithMe.domain.trip.error;

import team.backend.goWithMe.global.error.exception.ErrorCode;
import team.backend.goWithMe.global.error.exception.InvalidValueException;

public class SchedulePeriodInvalidException extends InvalidValueException {

    public SchedulePeriodInvalidException(ErrorCode errorCode) {
        super(errorCode);
    }
}
