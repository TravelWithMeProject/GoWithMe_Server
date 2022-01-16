package team.backend.goWithMe.domain.trip.exception;

import team.backend.goWithMe.global.error.exception.CommonErrorCode;
import team.backend.goWithMe.global.error.exception.ErrorCode;
import team.backend.goWithMe.global.error.exception.InvalidValueException;

public class TimeTableNameInvalidException extends InvalidValueException {
    public TimeTableNameInvalidException(String value) {
        super(value);
    }

    public TimeTableNameInvalidException(String value, CommonErrorCode errorCode) {
        super(value, errorCode);
    }
}
