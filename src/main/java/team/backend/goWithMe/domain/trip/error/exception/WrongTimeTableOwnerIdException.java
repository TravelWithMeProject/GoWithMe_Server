package team.backend.goWithMe.domain.trip.error.exception;

import lombok.Getter;
import team.backend.goWithMe.global.error.exception.ErrorCode;

@Getter
public class WrongTimeTableOwnerIdException extends RuntimeException {
    private final ErrorCode errorCode;


    public WrongTimeTableOwnerIdException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public WrongTimeTableOwnerIdException(ErrorCode errorCode) {
        super(errorCode.message());
        this.errorCode = errorCode;
    }


}