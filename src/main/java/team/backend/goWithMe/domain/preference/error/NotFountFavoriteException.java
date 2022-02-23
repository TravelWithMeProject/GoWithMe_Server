package team.backend.goWithMe.domain.preference.error;

import team.backend.goWithMe.global.error.exception.EntityNotFoundException;
import team.backend.goWithMe.global.error.exception.ErrorCode;

public class NotFountFavoriteException extends EntityNotFoundException {

    public NotFountFavoriteException(ErrorCode errorCode) {
        super(errorCode);
    }
}
