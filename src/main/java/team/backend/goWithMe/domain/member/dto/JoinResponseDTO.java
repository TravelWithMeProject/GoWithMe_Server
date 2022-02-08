package team.backend.goWithMe.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import team.backend.goWithMe.domain.member.domain.persist.Member;
import team.backend.goWithMe.domain.member.domain.vo.UserEmail;
import team.backend.goWithMe.domain.member.domain.vo.UserName;
import team.backend.goWithMe.domain.member.domain.vo.UserNickName;
import team.backend.goWithMe.domain.member.domain.vo.UserProfileImage;

import java.time.LocalDate;

@Getter
@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JoinResponseDTO {

    @JsonProperty("email")
    private UserEmail email;

    @JsonProperty("name")
    private UserName name;

    @JsonProperty("nickname")
    private UserNickName nickName;

    @JsonProperty("birth")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy")
    private LocalDate birth;

    @JsonProperty("profile")
    private UserProfileImage profileImage;

    public static JoinResponseDTO from(final Member member) {
        return new JoinResponseDTO(member.getEmail(), member.getName(), member.getNickname(), member.getBirth(),
                member.getProfileImage());
    }
}
