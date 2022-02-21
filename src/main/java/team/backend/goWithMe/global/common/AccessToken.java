package team.backend.goWithMe.global.common;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AccessToken {

    @NotBlank(message = "토큰이 존재하지 않습니다.")
    private String accessToken;

    @JsonValue
    public String accessToken() {
        return accessToken;
    }

    public static AccessToken from(String accessToken) {
        return new AccessToken(accessToken);
    }
}
