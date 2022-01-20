package team.backend.goWithMe.domain.trip.error;

import team.backend.goWithMe.global.error.exception.ErrorCode;
import team.backend.goWithMe.global.error.exception.InvalidValueException;

public class ScheduleTitleInvalidException extends InvalidValueException {
    public ScheduleTitleInvalidException(String value) {
        super(value);
    }

    public ScheduleTitleInvalidException(String value, ErrorCode errorCode) {
        super(value, errorCode);
    }
}
