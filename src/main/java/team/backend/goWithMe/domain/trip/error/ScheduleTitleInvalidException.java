package team.backend.goWithMe.domain.trip.error;

import team.backend.goWithMe.global.error.exception.ErrorCode;
import team.backend.goWithMe.global.error.exception.InvalidValueException;

public class ScheduleTitleInvalidException extends InvalidValueException {

    public ScheduleTitleInvalidException(ErrorCode errorCode) {
        super(errorCode);
    }
}
