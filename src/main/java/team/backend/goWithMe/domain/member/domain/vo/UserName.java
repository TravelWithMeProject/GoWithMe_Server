package team.backend.goWithMe.domain.member.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserName {

    @NotBlank(message = "이름은 필수 입력사항 입니다.")
    @Column(unique = true, nullable = false, length = 20)
    private String name;

    public static UserName from(final String name) {
        return new UserName(name);
    }

    @JsonValue
    public String getName() {
        return name;
    }
}
