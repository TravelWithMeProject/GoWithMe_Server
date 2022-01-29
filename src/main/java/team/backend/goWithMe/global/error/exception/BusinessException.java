package team.backend.goWithMe.global.error.exception;

import lombok.Getter;

// Business root Exception
@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode ErrorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.message());
        this.ErrorCode = errorCode;
    }
}
