package team.backend.goWithMe.domain.trip.error.exception;

import team.backend.goWithMe.global.error.exception.BusinessException;
import team.backend.goWithMe.global.error.exception.ErrorCode;

public class TimeTableNameInvalidException extends BusinessException {

    public TimeTableNameInvalidException(ErrorCode errorCode) {
        super(errorCode);
    }
}
