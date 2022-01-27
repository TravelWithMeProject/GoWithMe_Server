package team.backend.goWithMe.domain.mate.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.domain.mate.domain.persist.MateList;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MateRequestDto {

    private Long id;
    private String mateEmail;
    private String mateNickname;
    private String mateProfileImg;

    @Builder
    public MateRequestDto(Long id, String mateEmail, String mateNickname, String mateProfileImg, LocalDateTime birth) {
        this.id = id;
        this.mateEmail = mateEmail;
        this.mateNickname = mateNickname;
        this.mateProfileImg = mateProfileImg;
    }

}
