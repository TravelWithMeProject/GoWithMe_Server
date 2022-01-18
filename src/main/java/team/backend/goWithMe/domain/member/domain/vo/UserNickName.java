package team.backend.goWithMe.domain.member.domain.vo;

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
public final class UserNickName {

    @NotBlank(message = "별명은 필수 입력사항 입니다.")
    @Column(unique = true, nullable = false, length = 20)
    private String nickname;

    public static UserNickName from(final String nickname) {
        return new UserNickName(nickname);
    }

    @JsonValue
    public String userNickname() {
        return nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserNickName userNickName = (UserNickName) o;
        return Objects.equals(userNickname(), userNickName.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userNickname());
    }
}
