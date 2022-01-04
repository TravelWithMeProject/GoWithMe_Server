package team.backend.goWithMe.global.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.global.error.exception.CommonErrorCode;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonErrorResponse {

    private String message;
    private int status;
    private String code;

    private CommonErrorResponse(final CommonErrorCode code) {
        this.message = code.message();
        this.status = code.status();
        this.code = code.code();
    }

    public static CommonErrorResponse of(CommonErrorCode commonErrorCode) {
        return new CommonErrorResponse(commonErrorCode);
    }
}
