package team.backend.goWithMe.domain.trip.error;

import team.backend.goWithMe.global.error.exception.ErrorCode;
import team.backend.goWithMe.global.error.exception.InvalidValueException;

public class TimeTableNameInvalidException extends InvalidValueException {

    public TimeTableNameInvalidException(ErrorCode errorCode) {
        super(errorCode);
    }
}
