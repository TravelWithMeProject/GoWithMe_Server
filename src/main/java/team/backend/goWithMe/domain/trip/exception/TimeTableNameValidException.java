package team.backend.goWithMe.domain.trip.exception;

import team.backend.goWithMe.global.error.exception.ErrorCode;
import team.backend.goWithMe.global.error.exception.InvalidValueException;

public class TimeTableNameValidException extends InvalidValueException {
    public TimeTableNameValidException(String value) {
        super(value);
    }

    public TimeTableNameValidException(String value, ErrorCode errorCode) {
        super(value, errorCode);
    }
}
