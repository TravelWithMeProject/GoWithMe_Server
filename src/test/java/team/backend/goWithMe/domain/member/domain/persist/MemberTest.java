package team.backend.goWithMe.domain.member.domain.persist;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import team.backend.goWithMe.domain.member.domain.util.TestPasswordEncoder;
import team.backend.goWithMe.domain.member.domain.vo.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static team.backend.goWithMe.domain.member.domain.util.GivenMember.*;

class MemberTest {

    @Test
    @DisplayName("빌더 성공 테스트 : 제대로 실행 되야 하는 테스트")
    void builderSuccessTest() {

        Member member = createMember(USER_EMAIL, USER_PASSWORD, USER_NAME, ROLE_TYPE, USER_NICK_NAME,
                USER_BIRTH, USER_PROFILE);

        // then
        assertThat(member.getName().userName()).isEqualTo("KIM");
        assertThat(member.getEmail().userEmail()).isEqualTo("ilgolc@naver.com");
        assertThat(member.getPassword().userPassword()).isEqualTo(USER_PASSWORD.userPassword());
    }

    @Test
    @DisplayName("빌더 실패 테스트 : NullPointException이 터져야 한다.")
    void builderFailTest() {

        // given
        UserEmail givenEmail = UserEmail.from("ilgolc@naver.com");
        UserPassword givenPassword = UserPassword.from("1234");
        UserName givenName = UserName.from("KIM");
        UserNickName givenNickName = UserNickName.from("kim1234");
        LocalDate givenBirth = LocalDate.of(1995, 11, 23);
        UserProfileImage givenProfile = UserProfileImage.from("/static/dss/we23");

        // then
        assertThrows(NullPointerException.class, () -> {
             Member.builder()
                    .email(givenEmail)
                    .password(givenPassword)
                    .name(givenName)
                    .nickname(givenNickName)
                    .birth(givenBirth)
                    .profileImage(givenProfile)
                    .build();
        });
    }

    @Test
    @DisplayName("비밀번호 인코딩 테스트")
    void encodePassword() {
        final PasswordEncoder encoder = TestPasswordEncoder.initialize();
        Member member = createMember(USER_EMAIL, USER_PASSWORD, USER_NAME, ROLE_TYPE, USER_NICK_NAME,
                USER_BIRTH, USER_PROFILE).encode(encoder);

        assertAll(
                () -> assertThat(member.getPassword()).isNotEqualTo(USER_PASSWORD),
                () -> assertThat(encoder.matches(USER_PASSWORD.userPassword(),
                        member.getPassword().userPassword())).isTrue()
        );
    }

    @Test
    @DisplayName("회원 수정 로직 테스트")
    void updateTest() {
        Member member = createMember(USER_EMAIL, USER_PASSWORD, USER_NAME, ROLE_TYPE, USER_NICK_NAME,
                USER_BIRTH, USER_PROFILE);

        UserEmail updateEmail = UserEmail.from("ssar@naver.com");
        UserPassword updatePassword = UserPassword.from("6465");
        UserNickName updateNickName = UserNickName.from("GOLF");

        member.update(updateEmail, updatePassword, updateNickName);

        assertThat(member.getEmail()).isEqualTo(updateEmail);
        assertThat(member.getPassword()).isEqualTo(updatePassword);
        assertThat(member.getNickname()).isEqualTo(updateNickName);
    }
}