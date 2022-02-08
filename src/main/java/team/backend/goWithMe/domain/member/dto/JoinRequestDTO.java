package team.backend.goWithMe.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team.backend.goWithMe.domain.member.domain.persist.Member;
import team.backend.goWithMe.domain.member.domain.persist.RoleType;
import team.backend.goWithMe.domain.member.domain.vo.*;

import javax.validation.Valid;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
@ApiModel
@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class JoinRequestDTO {

    @Valid
    @JsonProperty("email")
    @ApiModelProperty(example = "ssar@gmail.com")
    private UserEmail userEmail;

    @Valid
    @JsonProperty("password")
    @ApiModelProperty(example = "1234")
    private UserPassword userPassword;

    @Valid
    @JsonProperty("name")
    @ApiModelProperty(example = "김딱구")
    private UserName name;

    @Valid
    @JsonProperty("nickname")
    @ApiModelProperty(example = "ssar")
    private UserNickName nickName;

    @JsonProperty("birth")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy")
    @ApiModelProperty(example = "10-10-1999")
    private LocalDate birth;

    @Valid
    @JsonProperty("profile")
    @ApiModelProperty(example = "/user/image/my_image.jpg")
    private UserProfileImage profileImage;

    public Member toEntity() {
        return Member.builder()
                .email(userEmail)
                .password(userPassword)
                .name(name)
                .nickname(nickName)
                .roleType(RoleType.USER)
                .birth(birth)
                .profileImage(profileImage)
                .build();
    }
}


