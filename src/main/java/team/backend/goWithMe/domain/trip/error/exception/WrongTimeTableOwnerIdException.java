package team.backend.goWithMe.domain.trip.error.exception;

import lombok.Getter;
import team.backend.goWithMe.global.error.exception.BusinessException;
import team.backend.goWithMe.global.error.exception.ErrorCode;

public class WrongTimeTableOwnerIdException extends BusinessException {

    public WrongTimeTableOwnerIdException(ErrorCode errorCode) {
        super(errorCode);
    }


}