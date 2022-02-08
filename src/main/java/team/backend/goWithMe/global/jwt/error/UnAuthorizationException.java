package team.backend.goWithMe.global.jwt.error;

import team.backend.goWithMe.global.error.exception.BusinessException;
import team.backend.goWithMe.global.error.exception.ErrorCode;

public class UnAuthorizationException extends BusinessException {

    public UnAuthorizationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
