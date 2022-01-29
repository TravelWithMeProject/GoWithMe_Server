package team.backend.goWithMe.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team.backend.goWithMe.domain.auth.details.MemberDetails;
import team.backend.goWithMe.domain.member.domain.persist.RoleType;
import team.backend.goWithMe.domain.member.domain.vo.*;

import javax.validation.Valid;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class JoinRequestDTO {

    @Valid
    @JsonProperty("email")
    private UserEmail userEmail;

    @Valid
    @JsonProperty("password")
    private UserPassword userPassword;

    @Valid
    @JsonProperty("name")
    private UserName name;

    @Valid
    @JsonProperty("nickname")
    private UserNickName nickName;

    @JsonProperty("birth")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy")
    private LocalDate birth;

    @Valid
    @JsonProperty("profile")
    private UserProfileImage profileImage;

    public MemberDetails toDetails() {
        log.debug("password : {}", userPassword.userPassword());
        log.debug("DtoEmail : {}", userEmail.userEmail());
        return MemberDetails.createDetails(userEmail, userPassword, name, nickName, birth,
                RoleType.USER, profileImage);
    }
}


