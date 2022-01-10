package team.backend.goWithMe.global.error.exception;

public class InvalidValueException extends BusinessException {

    public InvalidValueException(String value) {
        super(value, CommonErrorCode.INVALID_INPUT_VALUE);
    }

    public InvalidValueException(String value, CommonErrorCode commonErrorCode) {
        super(value, commonErrorCode);
    }
}
