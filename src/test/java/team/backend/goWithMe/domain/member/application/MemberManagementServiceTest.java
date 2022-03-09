package team.backend.goWithMe.domain.member.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import team.backend.goWithMe.domain.member.domain.persist.Member;
import team.backend.goWithMe.domain.member.domain.util.GivenMember;
import team.backend.goWithMe.domain.member.domain.vo.UserEmail;
import team.backend.goWithMe.domain.member.domain.vo.UserNickName;
import team.backend.goWithMe.domain.member.domain.vo.UserPassword;
import team.backend.goWithMe.domain.member.domain.vo.UserProfileImage;
import team.backend.goWithMe.domain.member.dto.MemberResponseDTO;
import team.backend.goWithMe.domain.member.error.exception.MemberNotFoundException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static team.backend.goWithMe.domain.member.domain.util.GivenMember.*;

@SpringBootTest
@Transactional
class MemberManagementServiceTest {

    @Autowired
    private MemberManagementService memberManagementService;

    @Autowired
    private PasswordEncoder encoder;

    static Member givenMember = Member.builder()
            .email(USER_EMAIL)
            .password(USER_PASSWORD)
            .name(USER_NAME)
            .nickname(USER_NICK_NAME)
            .roleType(ROLE_TYPE)
            .birth(USER_BIRTH)
            .profileImage(USER_PROFILE).build();

    @BeforeEach
    void createTest() {
        memberManagementService.create(givenMember);
    }

    @Test
    @DisplayName("회원 업데이트 로직 테스트")
    void updateTest() {
        // given
        Member updateMember = Member.builder()
                .email(UserEmail.from("ssar@gmail.com"))
                .password(UserPassword.from("12345"))
                .nickname(UserNickName.from("golf"))
                .profileImage(UserProfileImage.from("/user/image/new_image.jpeg"))
                .build();

        // when
        memberManagementService.update(updateMember, givenMember.getEmail());

        // then
        assertThat(givenMember.getEmail()).isEqualTo(UserEmail.from("ssar@gmail.com"));
        assertTrue(encoder.matches("12345", givenMember.getPassword().userPassword()));
        assertThat(givenMember.getNickname()).isEqualTo(UserNickName.from("golf"));
        assertThat(givenMember.getProfileImage()).isEqualTo(updateMember.getProfileImage());
    }

    @Test
    @DisplayName("회원 조회 로직 테스트")
    void findOneTest() {
        // given

        // when
        MemberResponseDTO member = memberManagementService.findOne(USER_EMAIL);

        // then
        assertThat(member.getEmail()).isEqualTo(USER_EMAIL);
        assertThat(member.getBirth()).isEqualTo(USER_BIRTH);
        assertThat(member.getName()).isEqualTo(USER_NAME);
        assertThat(member.getNickName()).isEqualTo(USER_NICK_NAME);
        assertThat(member.getProfileImage()).isEqualTo(USER_PROFILE);
    }

    @Test
    @DisplayName("회원 삭제 로직 테스트")
    void deleteTest() {
        // given

        // when
        memberManagementService.delete(givenMember.getEmail());

        // then
        assertThrows(MemberNotFoundException.class,
                () -> memberManagementService.findOne(givenMember.getEmail()));
    }

    @Test
    @DisplayName("회원 전체 조회 테스트")
    void findAllTest() {

    }
}