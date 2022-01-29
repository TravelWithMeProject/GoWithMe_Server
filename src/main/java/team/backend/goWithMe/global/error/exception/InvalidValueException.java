package team.backend.goWithMe.global.error.exception;

// InvalidException Root
public class InvalidValueException extends BusinessException {

    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }
}
