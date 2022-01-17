package team.backend.goWithMe.domain.trip.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import team.backend.goWithMe.global.error.exception.CommonErrorCode;
import team.backend.goWithMe.global.error.exception.ErrorCode;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TimeTableErrorCode implements ErrorCode {
    INVALID_TITLE_OR_NAME(400, "TT001", "제목은 반드시 값이 입력되어야 합니다."),
    PERIOD_MISMATCH_ERROR(400, "TT002", "기간 설정에 오류가 있습니다.");

    private final int status;
    private final String code;
    private final String message;

    TimeTableErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    @Override
    public int status() {
        return 0;
    }

    @Override
    public String message() {
        return null;
    }

    @Override
    public String code() {
        return null;
    }
}
