package team.backend.goWithMe.domain.member.error.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.domain.member.error.UserErrorCode;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserErrorResponse {

    private String message;
    private int status;
    private String code;

    private UserErrorResponse(final UserErrorCode code) {
        this.message = code.message();
        this.status = code.status();
        this.code = code.code();
    }

    public static UserErrorResponse of(UserErrorCode userErrorCode) {
        return new UserErrorResponse(userErrorCode);
    }
}
