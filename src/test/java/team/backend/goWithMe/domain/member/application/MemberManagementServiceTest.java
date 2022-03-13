package team.backend.goWithMe.domain.member.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import team.backend.goWithMe.domain.member.domain.persist.Member;
import team.backend.goWithMe.domain.member.domain.persist.MemberRepository;
import team.backend.goWithMe.domain.member.domain.util.GivenMember;
import team.backend.goWithMe.domain.member.domain.vo.*;
import team.backend.goWithMe.domain.member.dto.FindAllResponse;
import team.backend.goWithMe.domain.member.dto.MemberResponseDTO;
import team.backend.goWithMe.domain.member.error.exception.MemberNotFoundException;
import team.backend.goWithMe.domain.preference.domain.persist.Preference;
import team.backend.goWithMe.domain.preference.domain.persist.PreferenceRepository;
import team.backend.goWithMe.global.error.exception.ErrorCode;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static team.backend.goWithMe.domain.member.domain.persist.RoleType.USER;
import static team.backend.goWithMe.domain.member.domain.util.GivenMember.*;
import static team.backend.goWithMe.domain.preference.domain.util.GivenPreference.*;
import static team.backend.goWithMe.global.error.exception.ErrorCode.*;

@SpringBootTest
@Transactional
class MemberManagementServiceTest {

    @Autowired
    private MemberManagementService memberManagementService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private PreferenceRepository preferenceRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void createTest() {
        Member givenMember = Member.builder()
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .name(USER_NAME)
                .nickname(USER_NICK_NAME)
                .roleType(ROLE_TYPE)
                .birth(USER_BIRTH)
                .profileImage(USER_PROFILE).build();

        memberManagementService.create(givenMember);
    }

    @Test
    @DisplayName("회원 업데이트 로직 테스트")
    void updateTest() {
        // given
        Member member = memberRepository.findByEmail(USER_EMAIL).orElseThrow(
                () -> new MemberNotFoundException(USER_NOT_FOUND));

        Member updateMember = Member.builder()
                .email(UserEmail.from("ssar@gmail.com"))
                .password(UserPassword.from("12345"))
                .nickname(UserNickName.from("golf"))
                .profileImage(UserProfileImage.from("/user/image/new_image.jpeg"))
                .build();

        // when
        memberManagementService.update(updateMember, USER_EMAIL);

        // then
        assertThat(member.getEmail()).isEqualTo(updateMember.getEmail());
        assertTrue(encoder.matches("12345", member.getPassword().userPassword()));
        assertThat(member.getNickname()).isEqualTo(UserNickName.from("golf"));
        assertThat(member.getProfileImage()).isEqualTo(updateMember.getProfileImage());
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
        memberManagementService.delete(USER_EMAIL);

        // then
        assertThrows(MemberNotFoundException.class,
                () -> memberManagementService.findOne(USER_EMAIL));
    }

    @Test
    @DisplayName("Preference가 없으면 회원 전체를 조회해온다.")
    void findAllTest() {
        getMembers();
        Pageable pageable = PageRequest.of(0, 100, Sort.Direction.ASC, "id");

        List<FindAllResponse> members =
                memberManagementService.findAll(UserEmail.from("member1@gmail.com"), pageable);

        assertThat(members.size()).isEqualTo(11);

        for (FindAllResponse member : members) {
            System.out.println(member.getNickname().userNickname());
        }
    }

    @Test
    @DisplayName("Preference가 있으면 같은 값을 가진 회원들만 ")
    void findAllByPreference() {
        getMembers();
        getPreferences();
        Pageable pageable = PageRequest.of(0, 100, Sort.Direction.ASC, "id");

        List<FindAllResponse> members = memberManagementService.findAll(UserEmail.from("member1@gmail.com"), pageable);

        assertThat(members.size()).isEqualTo(6);
    }

    private void getPreferences() {
        for (int i = 0; i < 6; i++) {
            UserEmail email = UserEmail.from("member" + i + "@gmail.com");
            Member member = memberRepository.findByEmail(email).orElseThrow(
                    () -> new MemberNotFoundException(USER_NOT_FOUND));

            Preference preference = Preference.createPreference(givenArrival, givenAccommodation, givenPeriod, member);
            preferenceRepository.save(preference);
        }
    }

    private void getMembers() {
        for (int i = 0; i < 10; i++) {
            Member member = Member.builder()
                    .email(UserEmail.from("member" + i + "@gmail.com"))
                    .password(UserPassword.from("1234" + i))
                    .nickname(UserNickName.from("ssar" + i))
                    .name(UserName.from("kims" + i))
                    .roleType(USER)
                    .profileImage(UserProfileImage.from("/nothing"))
                    .birth(LocalDate.now())
                    .build();

            memberManagementService.create(member);
        }
    }
}