package team.backend.goWithMe.domain.mate.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class MateNickName {

    @Column(name = "nickName", nullable = false)
    @NotBlank(message = "친구 별명은 필수 입력 사항입니다.")
    private String mateNickname;

    public static MateNickName from(String mateNickname) {
        return new MateNickName(mateNickname);
    }

    @JsonValue
    public String mateNickname() {
        return mateNickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MateNickName that = (MateNickName) o;
        return Objects.equals(mateNickname(), that.mateNickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mateNickname());
    }
}
