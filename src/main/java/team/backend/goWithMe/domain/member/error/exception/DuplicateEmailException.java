package team.backend.goWithMe.domain.member.error.exception;

import team.backend.goWithMe.global.error.exception.BusinessException;
import team.backend.goWithMe.global.error.exception.ErrorCode;

public class DuplicateEmailException extends BusinessException {

    public DuplicateEmailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
