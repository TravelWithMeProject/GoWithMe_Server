package team.backend.goWithMe.domain.trip.error.exception;

import team.backend.goWithMe.global.error.exception.BusinessException;
import team.backend.goWithMe.global.error.exception.ErrorCode;

public class SchedulePeriodInvalidException extends BusinessException {

    public SchedulePeriodInvalidException(ErrorCode errorCode) {
        super(errorCode);
    }
}
