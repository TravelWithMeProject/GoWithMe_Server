package team.backend.goWithMe.domain.mate.error.exception;

import team.backend.goWithMe.global.error.exception.EntityNotFoundException;
import team.backend.goWithMe.global.error.exception.ErrorCode;

public class NoSuchMateException extends EntityNotFoundException {
    public NoSuchMateException(ErrorCode errorCode) {
        super(errorCode);
    }
}
