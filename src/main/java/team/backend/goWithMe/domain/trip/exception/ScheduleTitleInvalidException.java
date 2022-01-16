package team.backend.goWithMe.domain.trip.exception;

import team.backend.goWithMe.global.error.exception.CommonErrorCode;
import team.backend.goWithMe.global.error.exception.ErrorCode;
import team.backend.goWithMe.global.error.exception.InvalidValueException;

public class ScheduleTitleInvalidException extends InvalidValueException {
    public ScheduleTitleInvalidException(String value) {
        super(value);
    }

    public ScheduleTitleInvalidException(String value, CommonErrorCode errorCode) {
        super(value, errorCode);
    }
}
