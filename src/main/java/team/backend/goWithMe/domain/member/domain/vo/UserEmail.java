package team.backend.goWithMe.domain.member.domain.vo;

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
public final class UserEmail {

    @Email(message = "이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일은 필수 입력사항 입니다.")
    @Column(unique = true, nullable = false, length = 60)
    private String email;

    public static UserEmail from(final String email) {
        return new UserEmail(email);
    }

    @JsonValue
    public String userEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEmail userEmail = (UserEmail) o;
        return Objects.equals(userEmail(), userEmail.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userEmail());
    }
}
