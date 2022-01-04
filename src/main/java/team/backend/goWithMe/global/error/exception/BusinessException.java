package team.backend.goWithMe.global.error.exception;

public class BusinessException extends RuntimeException {

    private CommonErrorCode commonErrorCode;

    public BusinessException(String message, CommonErrorCode commonErrorCode) {
        super(message);
        this.commonErrorCode = commonErrorCode;
    }

    public BusinessException(CommonErrorCode commonErrorCode) {
        super(commonErrorCode.message());
        this.commonErrorCode = commonErrorCode;
    }

    public CommonErrorCode getErrorCode() {
        return commonErrorCode;
    }
}
