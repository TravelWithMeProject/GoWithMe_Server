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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserName {

    @NotBlank(message = "이름은 필수 입력사항 입니다.")
    @Column(unique = true, nullable = false, length = 20)
    private String name;

    public static UserName from(final String name) {
        return new UserName(name);
    }

    @JsonValue
    public String userName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserName userName = (UserName) o;
        return Objects.equals(userName(), userName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName());
    }
}
