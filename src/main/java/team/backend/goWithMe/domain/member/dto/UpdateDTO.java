package team.backend.goWithMe.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team.backend.goWithMe.domain.member.domain.persist.Member;
import team.backend.goWithMe.domain.member.domain.vo.UserEmail;
import team.backend.goWithMe.domain.member.domain.vo.UserNickName;
import team.backend.goWithMe.domain.member.domain.vo.UserPassword;
import team.backend.goWithMe.domain.member.domain.vo.UserProfileImage;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class UpdateDTO {

    @JsonProperty("email")
    private UserEmail email;

    @JsonProperty("password")
    private UserPassword password;

    @JsonProperty("nickname")
    private UserNickName nickName;

    @JsonProperty("profile")
    private UserProfileImage profileImage;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickName)
                .profileImage(profileImage)
                .build();
    }
}
