package team.backend.goWithMe.domain.mate.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MateEmail mateEmail = (MateEmail) o;
        return Objects.equals(mateEmail(), mateEmail.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mateEmail());
    }
}
