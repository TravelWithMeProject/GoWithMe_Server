package team.backend.goWithMe.domain.mate.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
public class MateListDTO {

    private Long id;
    private String mateEmail;
    private String mateNickname;
    private String mateProfileImg;
    private LocalDateTime birth;
}
