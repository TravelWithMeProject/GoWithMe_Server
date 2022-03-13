package team.backend.goWithMe.domain.preference.error;

import team.backend.goWithMe.global.error.exception.BusinessException;
import team.backend.goWithMe.global.error.exception.ErrorCode;

public class OverPeriodException extends BusinessException {

    public OverPeriodException(ErrorCode errorCode) {
        super(errorCode);
    }
}
