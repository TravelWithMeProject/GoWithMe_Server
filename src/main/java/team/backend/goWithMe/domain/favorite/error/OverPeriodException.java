package team.backend.goWithMe.domain.favorite.error;

import team.backend.goWithMe.global.error.exception.BusinessException;
import team.backend.goWithMe.global.error.exception.ErrorCode;

public class OverPeriodException extends BusinessException {

    public OverPeriodException(ErrorCode errorCode) {
        super(errorCode);
    }
}
