package team.backend.goWithMe.domain.trip.error.exception;

import team.backend.goWithMe.global.error.exception.ErrorCode;

public class SchedulePeriodInvalidException extends TimeTableBusinessException {
    public SchedulePeriodInvalidException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public SchedulePeriodInvalidException(ErrorCode errorCode) {
        super(errorCode);
    }
}
