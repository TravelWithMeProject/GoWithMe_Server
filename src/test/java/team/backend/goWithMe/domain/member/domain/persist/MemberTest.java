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
    @DisplayName("유저 생성 테스트 : 제대로 실행 되야 하는 테스트")
    void builderSuccessTest() {

        Member member = createMember();

        // then
        assertThat(member.getName().userName()).isEqualTo("KIM");
        assertThat(member.getEmail().userEmail()).isEqualTo("ilgolc@naver.com");
        assertThat(member.getPassword().userPassword()).isEqualTo(USER_PASSWORD.userPassword());
    }

    @Test
    @DisplayName("비밀번호 인코딩 테스트")
    void encodePassword() {
        final PasswordEncoder encoder = TestPasswordEncoder.initialize();
        Member member = createMember().encode(encoder);

        assertAll(
                () -> assertThat(member.getPassword()).isNotEqualTo(USER_PASSWORD),
                () -> assertThat(encoder.matches(USER_PASSWORD.userPassword(),
                        member.getPassword().userPassword())).isTrue()
        );
    }

    @Test
    @DisplayName("회원 수정 로직 테스트")
    void updateTest() {
        Member member = createMember();

        UserEmail updateEmail = UserEmail.from("ssar@naver.com");
        UserPassword updatePassword = UserPassword.from("6465");
        UserNickName updateNickName = UserNickName.from("GOLF");
        UserProfileImage updateProfile = UserProfileImage.from("/develop");

        Member updatedMember = of(updateEmail, updatePassword, USER_NAME, updateNickName, USER_BIRTH,
                updateProfile);

        final PasswordEncoder encoder = TestPasswordEncoder.initialize();

        member.update(updatedMember, encoder);

        assertThat(member.getEmail()).isEqualTo(updateEmail);
        assertThat(member.getNickname()).isEqualTo(updateNickName);
        assertThat(member.getPassword()).isNotEqualTo(updatePassword);
    }
}