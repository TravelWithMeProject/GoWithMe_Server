package team.backend.goWithMe.domain.trip.error.exception;

import team.backend.goWithMe.domain.trip.error.TimeTableErrorCode;

public class TimeTableBusinessException extends RuntimeException{

    private final TimeTableErrorCode timeTableErrorCode;

    public TimeTableBusinessException(String message, TimeTableErrorCode errorCode) {
        super(message);
        this.timeTableErrorCode = errorCode;
    }

    public TimeTableBusinessException(TimeTableErrorCode errorCode) {
        super(errorCode.message());
        this.timeTableErrorCode = errorCode;
    }

    public TimeTableErrorCode getTimeTableErrorCode() {
        return timeTableErrorCode;
    }
}
