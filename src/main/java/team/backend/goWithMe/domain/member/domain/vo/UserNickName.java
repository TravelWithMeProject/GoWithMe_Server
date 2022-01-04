package team.backend.goWithMe.domain.member.domain.vo;

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
public class UserNickName {

    @NotBlank(message = "별명은 필수 입력사항 입니다.")
    @Column(unique = true, nullable = false, length = 20)
    private String nickname;

    public static UserNickName from(String nickname) {
        return new UserNickName(nickname);
    }

    @JsonValue
    public String getNickname() {
        return nickname;
    }
}
