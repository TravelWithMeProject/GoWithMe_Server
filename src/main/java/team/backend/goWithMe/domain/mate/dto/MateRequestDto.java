package team.backend.goWithMe.domain.mate.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import team.backend.goWithMe.domain.mate.domain.persist.MateList;
import team.backend.goWithMe.domain.mate.domain.vo.MateEmail;
import team.backend.goWithMe.domain.mate.domain.vo.MateNickName;
import team.backend.goWithMe.domain.mate.domain.vo.MateProfileImg;
import team.backend.goWithMe.domain.member.domain.persist.Member;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@JsonTypeName("mate")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class MateRequestDto {

    @JsonProperty("email")
    private MateEmail mateEmail;

    @JsonProperty("nickname")
    private MateNickName mateNickname;

    @JsonProperty("profile_img")
    private MateProfileImg mateProfileImg;

    @JsonProperty("birth")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy")
    private LocalDateTime birth;

    public MateList toEntity(Member member) {
        return MateList.builder()
                .mateEmail(mateEmail)
                .mateNickName(mateNickname)
                .mateProfileImg(mateProfileImg)
                .birth(birth)
                .build();
    }

}
