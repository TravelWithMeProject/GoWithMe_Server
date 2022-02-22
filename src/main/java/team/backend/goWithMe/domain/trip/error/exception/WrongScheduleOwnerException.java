package team.backend.goWithMe.domain.trip.error.exception;

import lombok.Getter;
import team.backend.goWithMe.global.error.exception.BusinessException;
import team.backend.goWithMe.global.error.exception.ErrorCode;

@Getter
public class WrongScheduleOwnerException extends BusinessException {
    private final ErrorCode errorCode;

    public WrongScheduleOwnerException(ErrorCode errorCode) {
        super(errorCode.message(), errorCode);
        this.errorCode = errorCode;
    }
}
