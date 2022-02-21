package team.backend.goWithMe.global.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", "잘못된 입력입니다."),
    METHOD_NOT_ALLOWED(405, "C002", "잘못된 요청입니다."),
    ENTITY_NOT_FOUND(400, "C003", "개체를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(500, "C004", "서버 내부 오류 입니다."),
    INVALID_TYPE_VALUE(400, "C005", "잘못된 타입 입니다."),
    HANDLE_ACCESS_DENIED(403, "C006", "잘못된 접근 입니다."),

    // Member
    EMAIL_DUPLICATION(400, "M001", "중복된 이메일 입니다."),
    USER_NOT_FOUND(400, "M002", "다시 로그인 시도해 주세요"),
    PASSWORD_NULL_ERROR(400, "M003", "올바른 비밀번호 입력이 아닙니다."),
    PASSWORD_MISS_MATCH(400, "M004", "비밀번호가 일치하지 않습니다."),
    MEMBER_NOT_FOUND(400, "M005", "해당 회원을 찾을 수 없습니다."),

    // Token, Auth
    Token_NOT_FOUND(401, "T001", "유효하지 않은 토큰입니다."),
    UN_AUTHORIZATION_ERROR(401, "T002", "이미 탈퇴한 회원입니다."),

    // Favorite
    OVER_PERIOD_ERROR(400, "F001", "날짜 입력이 잘못 되었습니다."),
    NOT_FOUND_FAVORITE(400, "F002", "해당 개체를 찾을 수 없습니다.")

    ;


    // travel domain

    // Mate domain

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }


    public int status() {
        return status;
    }

    public String message() {
        return message;
    }

    public String code() {
        return code;
    }
}
