package team.backend.goWithMe.global.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenDTO {

    @JsonProperty("accessToken")
    private AccessToken accessToken;

    @JsonProperty("refreshToken")
    private RefreshToken refreshToken;

    public static TokenDTO create(final AccessToken accessToken, final RefreshToken refreshToken) {
        return new TokenDTO(accessToken, refreshToken);
    }
}
