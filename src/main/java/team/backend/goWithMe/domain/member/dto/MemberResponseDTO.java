package team.backend.goWithMe.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.domain.member.domain.persist.Member;
import team.backend.goWithMe.domain.member.domain.vo.*;

import java.time.LocalDate;

@Getter
@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponseDTO {

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

    /**
     *  생성 로직
     */
    public static MemberResponseDTO create(final Member member) {
        return new MemberResponseDTO(member.getEmail(), member.getName(),
                member.getNickname(), member.getBirth(), member.getProfileImage());
    }
}
