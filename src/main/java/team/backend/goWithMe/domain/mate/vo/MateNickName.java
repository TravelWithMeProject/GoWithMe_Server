package team.backend.goWithMe.domain.mate.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MateNickName {

    @Column(name = "nickName", nullable = false)
    @NotBlank(message = "친구 별명은 필수 입력 사항입니다.")
    private String mateNickname;

    public static MateNickName from(String mateNickname) {
        return new MateNickName(mateNickname);
    }

    @JsonValue
    public String getMateNickname() {
        return mateNickname;
    }
}
