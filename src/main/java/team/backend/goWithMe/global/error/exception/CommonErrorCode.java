package team.backend.goWithMe.global.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CommonErrorCode implements ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", "잘못된 입력입니다."),
    METHOD_NOT_ALLOWED(405, "C002", "잘못된 요청입니다."),
    ENTITY_NOT_FOUND(400, "C003", "개체를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(500, "C004", "서버 내부 오류 입니다."),
    INVALID_TYPE_VALUE(400, "C005", "잘못된 타입 입니다."),
    HANDLE_ACCESS_DENIED(403, "C006", "잘못된 접근 입니다.");

    private final String code;
    private final String message;
    private final int status;

    CommonErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }


    @Override
    public int status() {
        return status;
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public String code() {
        return code;
    }
}
