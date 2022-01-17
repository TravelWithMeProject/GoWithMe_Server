package team.backend.goWithMe.domain.trip.error.exception;

import team.backend.goWithMe.domain.trip.error.TimeTableErrorCode;
import team.backend.goWithMe.domain.trip.error.exception.TimeTableBusinessException;
import team.backend.goWithMe.global.error.exception.CommonErrorCode;
import team.backend.goWithMe.global.error.exception.InvalidValueException;

public class ScheduleTitleInvalidException extends TimeTableBusinessException {

    public ScheduleTitleInvalidException(String message, TimeTableErrorCode errorCode) {
        super(message, errorCode);
    }

    public ScheduleTitleInvalidException(TimeTableErrorCode errorCode) {
        super(errorCode);
    }
}
