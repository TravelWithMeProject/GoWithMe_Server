package team.backend.goWithMe.domain.trip.error.exception;

import team.backend.goWithMe.global.error.exception.ErrorCode;

public class TimeTableBusinessException extends RuntimeException{

    private final ErrorCode errorCode;

    public TimeTableBusinessException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public TimeTableBusinessException(ErrorCode errorCode) {
        super(errorCode.message());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
