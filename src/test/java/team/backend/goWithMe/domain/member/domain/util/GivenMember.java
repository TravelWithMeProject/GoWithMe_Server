package team.backend.goWithMe.domain.member.domain.util;

import team.backend.goWithMe.domain.member.domain.persist.Member;
import team.backend.goWithMe.domain.member.domain.persist.RoleType;
import team.backend.goWithMe.domain.member.domain.vo.*;

import java.time.LocalDate;

public class GivenMember {

    public static final UserEmail USER_EMAIL = UserEmail.from("ilgolc@naver.com");
    public static final UserPassword USER_PASSWORD = UserPassword.from("1234");
    public static final UserName USER_NAME = UserName.from("KIM");
    public static final RoleType ROLE_TYPE = RoleType.USER;
    public static final UserNickName USER_NICK_NAME = UserNickName.from("kim1234");
    public static final LocalDate USER_BIRTH = LocalDate.of(1995, 11, 23);
    public static final UserProfileImage USER_PROFILE = UserProfileImage.from("/static/dss/we23");

    public static Member createMember() {

        return Member.builder()
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .name(USER_NAME)
                .roleType(RoleType.USER)
                .nickname(USER_NICK_NAME)
                .birth(USER_BIRTH)
                .profileImage(USER_PROFILE)
                .build();
    }

    public static Member of(final UserEmail email, final UserPassword password, final UserName name,
                            final UserNickName nickname, final LocalDate birth, final UserProfileImage profile) {

        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .roleType(RoleType.USER)
                .nickname(nickname)
                .birth(birth)
                .profileImage(profile).build();
    }
}
