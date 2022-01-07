package team.backend.goWithMe.domain.member.domain.persist;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team.backend.goWithMe.domain.member.domain.vo.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class MemberTest {

    @Test
    @DisplayName("빌더 성공 테스트 : 제대로 실행 되야 하는 테스트")
    void builderSuccessTest() {

        // given
        UserEmail givenEmail = UserEmail.from("ilgolc@naver.com");
        UserPassword givenPassword = UserPassword.from("1234");
        UserName givenName = UserName.from("KIM");
        RoleType roleType = RoleType.USER;
        UserNickName givenNickName = UserNickName.from("kim1234");
        LocalDate givenBirth = LocalDate.of(1995, 11, 23);
        UserProfileImage givenProfile = UserProfileImage.from("/static/dss/we23");

        // when
        Member member = Member.builder()
                .email(givenEmail)
                .password(givenPassword)
                .name(givenName)
                .roleType(roleType)
                .nickname(givenNickName)
                .birth(givenBirth)
                .profileImage(givenProfile)
                .build();

        // then
        assertThat(member.getName().userName()).isEqualTo("KIM");
        assertThat(member.getEmail().userEmail()).isEqualTo("ilgolc@naver.com");
        assertThat(member.getPassword().userPassword()).isEqualTo(givenPassword.userPassword());
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
            Member member = Member.builder()
                    .email(givenEmail)
                    .password(givenPassword)
                    .name(givenName)
                    .nickname(givenNickName)
                    .birth(givenBirth)
                    .profileImage(givenProfile)
                    .build();
        });
    }
}