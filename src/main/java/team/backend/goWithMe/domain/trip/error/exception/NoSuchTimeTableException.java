package team.backend.goWithMe.domain.trip.error.exception;

import team.backend.goWithMe.global.error.exception.EntityNotFoundException;
import team.backend.goWithMe.global.error.exception.ErrorCode;

public class NoSuchTimeTableException extends EntityNotFoundException {
    public NoSuchTimeTableException(ErrorCode errorCode) {
        super(errorCode);
    }
}