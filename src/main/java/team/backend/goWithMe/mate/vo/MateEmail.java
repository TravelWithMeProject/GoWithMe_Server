package team.backend.goWithMe.mate.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public final class MateEmail {

    @Email(message = "이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일은 필수 입력 사항입니다.")
    @Column(nullable = false)
    private String email;

    public static MateEmail from(final String email) {
        return new MateEmail(email);
    }

    @JsonValue
    public String mateEmail() {
        return email;
    }
}
