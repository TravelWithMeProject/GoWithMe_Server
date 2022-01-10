package team.backend.goWithMe.domain.member.domain.persist;

import io.jsonwebtoken.lang.Assert;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import team.backend.goWithMe.domain.member.domain.vo.*;
import team.backend.goWithMe.global.common.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
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

    @Builder
    public Member(UserEmail email, UserPassword password, UserName name, RoleType roleType, UserNickName nickname, LocalDate birth, UserProfileImage profileImage) {

        // null 방지 검증 로직
        Assert.hasText(email.userEmail());
        Assert.hasText(password.userPassword());
        Assert.hasText(name.userName());
        Assert.hasText(roleType.name());
        Assert.hasText(nickname.userNickname());
        Assert.hasText(profileImage.userProfileImage());

        this.email = email;
        this.password = password;
        this.name = name;
        this.roleType = roleType;
        this.nickname = nickname;
        this.birth = birth;
        this.profileImage = profileImage;
    }


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

    // 비밀번호 해시화
    public Member encode(final PasswordEncoder encoder) {
        password = UserPassword.encode(password.userPassword(), encoder);
        return this;
    }
}
