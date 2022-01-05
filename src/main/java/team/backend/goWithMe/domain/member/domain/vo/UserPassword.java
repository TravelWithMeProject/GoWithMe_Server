package team.backend.goWithMe.domain.member.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import team.backend.goWithMe.domain.member.error.UserErrorCode;
import team.backend.goWithMe.domain.member.error.exception.PasswordMissMatchException;
import team.backend.goWithMe.domain.member.error.exception.PasswordNullException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class UserPassword {

    @NotBlank(message = "비밀번호는 필수 입력사항 입니다.")
    @Column(unique = true, nullable = false, length = 100)
    private String password;

    public static UserPassword encode(final String rawPassword, final PasswordEncoder passwordEncoder) {
        validateBlank(rawPassword);
        return new UserPassword(passwordEncoder.encode(rawPassword));
    }

    public static UserPassword from(final String password) {
        return new UserPassword(password);
    }

    private static void validateBlank(String rawPassword) {
        if (Objects.isNull(rawPassword) || rawPassword.isBlank()) {
            throw new PasswordNullException(UserErrorCode.PASSWORD_NULL_ERROR.message());
        }
    }

    public void matches(final UserPassword other, final PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(other.password, this.password)) {
            throw new PasswordMissMatchException(UserErrorCode.PASSWORD_MISS_MATCH.message());
        }
    }

    @JsonValue
    public String userPassword() {
        return password;
    }
}
