package team.backend.goWithMe.domain.mate.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;
import team.backend.goWithMe.domain.mate.domain.persist.MateList;
import team.backend.goWithMe.domain.mate.domain.vo.MateEmail;
import team.backend.goWithMe.domain.mate.domain.vo.MateNickName;
import team.backend.goWithMe.domain.mate.domain.vo.MateProfileImg;

import java.time.LocalDateTime;

@Getter
@JsonTypeName("mate")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@AllArgsConstructor
@NoArgsConstructor
public class MateResponseDto {

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

    @Builder
    public MateResponseDto (MateList mateList) {
        this.mateEmail = mateList.getMateEmail();
        this.mateNickname = mateList.getMateNickname();
        this.mateProfileImg = mateList.getMateProfileImg();
        this.birth = mateList.getBirth();
    }
}
