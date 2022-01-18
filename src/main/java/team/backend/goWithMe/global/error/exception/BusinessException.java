package team.backend.goWithMe.global.error.exception;

// Business root Exception
public class BusinessException extends RuntimeException {

    private final ErrorCode ErrorCode;

    public BusinessException(String message, ErrorCode errorCode) {
        super(message);
        this.ErrorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return ErrorCode;
    }
}
