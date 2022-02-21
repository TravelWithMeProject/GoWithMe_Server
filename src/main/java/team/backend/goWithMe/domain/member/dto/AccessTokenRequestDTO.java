package team.backend.goWithMe.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.global.common.AccessToken;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccessTokenRequestDTO {

    @JsonProperty("accessToken")
    private AccessToken accessToken;
}
