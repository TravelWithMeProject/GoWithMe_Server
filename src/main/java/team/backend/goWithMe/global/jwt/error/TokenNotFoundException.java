package team.backend.goWithMe.global.jwt.error;

import team.backend.goWithMe.global.error.exception.EntityNotFoundException;
import team.backend.goWithMe.global.error.exception.ErrorCode;

public class TokenNotFoundException extends EntityNotFoundException {

    public TokenNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
