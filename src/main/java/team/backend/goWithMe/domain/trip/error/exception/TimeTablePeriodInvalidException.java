package team.backend.goWithMe.domain.trip.error.exception;


import team.backend.goWithMe.global.error.exception.ErrorCode;

public class TimeTablePeriodInvalidException extends TimeTableBusinessException {
    public TimeTablePeriodInvalidException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public TimeTablePeriodInvalidException(ErrorCode errorCode) {
        super(errorCode);
    }
}
