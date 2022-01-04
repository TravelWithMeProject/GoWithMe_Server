package team.backend.goWithMe.domain.member.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import team.backend.goWithMe.global.error.exception.ErrorCode;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserErrorCode implements ErrorCode {

    EMAIL_DUPLICATION(400, "M001", "중복된 이메일 입니다."),
    LOGIN_INPUT_INVALID(400, "M002", "다시 로그인 시도해 주세요"),
    PASSWORD_NULL_ERROR(400, "M003", "올바른 비밀번호 입력이 아닙니다."),
    PASSWORD_MISS_MATCH(400, "M004", "비밀번호가 일치하지 않습니다.")
    ;

    private final int status;
    private final String code;
    private final String message;

    UserErrorCode(int status, String code, String message) {
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
