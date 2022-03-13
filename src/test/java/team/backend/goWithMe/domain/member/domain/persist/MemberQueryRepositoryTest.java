package team.backend.goWithMe.domain.member.domain.persist;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import team.backend.goWithMe.domain.member.domain.util.GivenMember;
import team.backend.goWithMe.domain.member.domain.vo.*;
import team.backend.goWithMe.domain.member.error.exception.MemberNotFoundException;
import team.backend.goWithMe.domain.preference.domain.persist.Preference;
import team.backend.goWithMe.domain.preference.domain.persist.PreferenceRepository;
import team.backend.goWithMe.domain.preference.domain.util.GivenPreference;
import team.backend.goWithMe.domain.preference.domain.vo.Accommodation;
import team.backend.goWithMe.domain.preference.domain.vo.PreferenceArrival;
import team.backend.goWithMe.global.error.exception.ErrorCode;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static team.backend.goWithMe.domain.member.domain.persist.RoleType.*;
import static team.backend.goWithMe.domain.preference.domain.util.GivenPreference.*;

@SpringBootTest
@Transactional
class MemberQueryRepositoryTest {

    @Autowired
    MemberQueryRepository memberQueryRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PreferenceRepository preferenceRepository;

    @BeforeEach
    void init() {
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

            memberRepository.save(member);
        }

        for (int i = 0; i < 6; i++) {
            UserEmail email = UserEmail.from("member" + i + "@gmail.com");
            Member member = memberRepository.findByEmail(email).orElseThrow(
                    () -> new MemberNotFoundException(ErrorCode.USER_NOT_FOUND));

            Preference preference = Preference.createPreference(givenArrival, givenAccommodation, givenPeriod, member);
            preferenceRepository.save(preference);
        }
    }

    @Test
    @DisplayName("도착지에 관련된 쿼리만 나간다.")
    void arrivalQueryTest() {
        // given
        Member givenMember = GivenMember.createMember();
        Preference preference = Preference.createPreference
                (PreferenceArrival.from("USA"), Accommodation.from("GUESTHOUSE"), givenPeriod, givenMember);
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "id");

        memberRepository.save(givenMember);
        preferenceRepository.save(preference);

        // when
        List<Member> members = memberQueryRepository.findAllByFavorite(givenMember.getPreference(), pageable)
                .stream()
                .filter(e -> !e.getEmail().equals(givenMember.getEmail()))
                .collect(Collectors.toList());

        // then
        assertThat(members.size()).isEqualTo(6);
    }
}