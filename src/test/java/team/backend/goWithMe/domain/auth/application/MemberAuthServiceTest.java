package team.backend.goWithMe.domain.auth.application;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import team.backend.goWithMe.domain.member.domain.vo.UserEmail;
import team.backend.goWithMe.domain.member.domain.vo.UserPassword;
import team.backend.goWithMe.global.common.TokenDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class MemberAuthServiceTest {

    @Autowired private MemberAuthService memberAuthService;

    @Test
    @DisplayName("로그인 성공 테스트")
    void authorizedTest() {
        UserEmail givenEmail = UserEmail.from("ilgolc@naver.com");
        UserPassword givenPassword = UserPassword.from("1234");

        TokenDTO authorize = memberAuthService.authorize(givenEmail, givenPassword);
    }
}