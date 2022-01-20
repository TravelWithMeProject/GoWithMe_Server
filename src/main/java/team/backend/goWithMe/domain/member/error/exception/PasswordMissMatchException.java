package team.backend.goWithMe.domain.member.error.exception;

import team.backend.goWithMe.global.error.exception.BusinessException;
import team.backend.goWithMe.global.error.exception.ErrorCode;

public class PasswordMissMatchException extends BusinessException {

    public PasswordMissMatchException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
