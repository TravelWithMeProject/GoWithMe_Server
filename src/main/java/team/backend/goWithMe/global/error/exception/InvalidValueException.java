package team.backend.goWithMe.global.error.exception;

// InvalidException Root
public class InvalidValueException extends BusinessException {

    public InvalidValueException(String value) {
        super(value, ErrorCode.INVALID_INPUT_VALUE);
    }

    public InvalidValueException(String value, ErrorCode commonErrorCode) {
        super(value, commonErrorCode);
    }
}
