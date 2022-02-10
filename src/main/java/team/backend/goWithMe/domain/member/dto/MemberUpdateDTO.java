package team.backend.goWithMe.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel
@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class MemberUpdateDTO {

    @JsonProperty("email")
    @ApiModelProperty(example = "golf@gmail.com")
    private UserEmail email;

    @JsonProperty("password")
    @ApiModelProperty(example = "3245")
    private UserPassword password;

    @JsonProperty("nickname")
    @ApiModelProperty(example = "golf")
    private UserNickName nickName;

    @JsonProperty("profile")
    @ApiModelProperty(example = "/user/image/new_image.jpeg")
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
