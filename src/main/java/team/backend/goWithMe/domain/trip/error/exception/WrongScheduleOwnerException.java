package team.backend.goWithMe.domain.trip.error.exception;

import team.backend.goWithMe.global.error.exception.BusinessException;
import team.backend.goWithMe.global.error.exception.ErrorCode;

public class WrongScheduleOwnerException extends BusinessException {

    public WrongScheduleOwnerException(ErrorCode errorCode) {
        super(errorCode);
    }
}
