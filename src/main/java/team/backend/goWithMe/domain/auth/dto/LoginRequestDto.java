package team.backend.goWithMe.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.domain.member.domain.vo.UserEmail;
import team.backend.goWithMe.domain.member.domain.vo.UserPassword;


@Getter
@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class LoginRequestDto {

    @JsonProperty("email")
    private UserEmail email;

    @JsonProperty("password")
    private UserPassword password;
}
