package team.backend.goWithMe.domain.trip.error.exception;

import team.backend.goWithMe.global.error.exception.EntityNotFoundException;
import team.backend.goWithMe.global.error.exception.ErrorCode;

public class NoSuchScheduleException extends EntityNotFoundException {

    public NoSuchScheduleException(ErrorCode errorCode) {
        super(errorCode);
    }
}
