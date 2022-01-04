package team.backend.goWithMe.domain.member.domain.persist;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import team.backend.goWithMe.domain.member.domain.vo.*;
import team.backend.goWithMe.global.common.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Embedded
    private UserEmail email;

    @Embedded
    private UserPassword password;

    @Embedded
    private UserName name;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_type", length = 20)
    private RoleType roleType;

    @Embedded
    private UserNickName nickname;

    @DateTimeFormat
    private LocalDate birth;

    @Embedded
    private UserProfileImage profileImage;

    /**
     * 비즈 니스 로직
     */
    private void changeEmail(UserEmail email) {
        this.email = email;
    }

    private void changePassword(UserPassword password) {
        this.password = password;
    }

    private void changeNickName(UserNickName nickname) {
        this.nickname = nickname;
    }

    public void update(final UserEmail email, final UserPassword password, final UserNickName nickname) {
        changeEmail(email);
        changePassword(password);
        changeNickName(nickname);
    }
}
