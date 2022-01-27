package team.backend.goWithMe.domain.mate.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.domain.mate.domain.persist.MateList;

import java.time.LocalDateTime;

@Getter
public class MateResponseDto {

    private Long id;
    private String mateEmail;
    private String mateNickname;
    private String mateProfileImg;

    public MateResponseDto(MateList entity) {
        this.id = entity.getId();
        this.mateEmail = entity.getMateEmail().mateEmail();
        this.mateNickname = entity.getMateNickname().mateNickname();
        this.mateProfileImg = entity.getMateProfileImg().mateProfileImg();
    }
}
