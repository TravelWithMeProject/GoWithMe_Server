package team.backend.goWithMe.domain.trip.error.exception;


import team.backend.goWithMe.global.error.exception.ErrorCode;

public class ScheduleTitleInvalidException extends TimeTableBusinessException {

    public ScheduleTitleInvalidException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ScheduleTitleInvalidException(ErrorCode errorCode) {
        super(errorCode);
    }
}
