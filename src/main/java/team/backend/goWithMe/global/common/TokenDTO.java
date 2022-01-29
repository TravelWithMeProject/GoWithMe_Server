package team.backend.goWithMe.global.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenDTO {

    private AccessToken accessToken;
    private RefreshToken refreshToken;

    public static TokenDTO create(final AccessToken accessToken, final RefreshToken refreshToken) {
        return new TokenDTO(accessToken, refreshToken);
    }
}
