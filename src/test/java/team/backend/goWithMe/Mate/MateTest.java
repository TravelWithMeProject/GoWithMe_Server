package team.backend.goWithMe.Mate;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import org.junit.jupiter.api.DisplayName;
import team.backend.goWithMe.domain.mate.domain.MateList;
import team.backend.goWithMe.domain.mate.vo.MateEmail;
import team.backend.goWithMe.domain.mate.vo.MateNickName;
import team.backend.goWithMe.domain.mate.vo.MateProfileImg;

public class MateTest {

    @Test
    public void buildMateTest() {

        final String value = "shinsw6455@naver.com";
        final MateEmail mateEmail = MateEmail.from(value);

        final String nickname = "ssw";
        final MateNickName mateNickName = MateNickName.from(nickname);

        final String img = "dfdf";
        final MateProfileImg mateProfileImg = MateProfileImg.from(img);

        final MateList mateList = MateListBuilder.build(mateEmail, mateNickName, mateProfileImg);

        Assertions.assertThat(mateList.getMateEmail()).isEqualTo(mateEmail);
        Assertions.assertThat(mateList.getMateNickname()).isEqualTo(mateNickName);
        Assertions.assertThat(mateList.getMateProfileImg()).isEqualTo(mateProfileImg);

    }

    @Test
    @DisplayName("이메일 변경 테스트")
    public void changeEmailTest() {
        final MateList mateList = MateListBuilder.build();

        final String value = "shinsw645@naver.com";
        final MateEmail mateEmail = MateEmail.from(value);

        mateList.changeEmail(mateEmail);

        Assertions.assertThat(mateList.getMateEmail()).isEqualTo(mateEmail);
    }

    @Test
    @DisplayName("닉네임 변경 테스트")
    public void changeNickNameTest() {
        final MateList mateList = MateListBuilder.build();

        final String nickname = "ssw";
        final MateNickName mateNickName = MateNickName.from(nickname);

        mateList.changeNickName(mateNickName);

        Assertions.assertThat(mateList.getMateNickname()).isEqualTo(mateNickName);
    }

    @Test
    @DisplayName("프로필 이미지 변경 테스트")
    public void changeMateProfileImgTest() {
        final MateList mateList = MateListBuilder.build();

        final String img = "dfqf";
        final MateProfileImg mateProfileImg = MateProfileImg.from(img);

        mateList.changeMateProfileImg(mateProfileImg);

        Assertions.assertThat(mateList.getMateProfileImg()).isEqualTo(mateProfileImg);
    }
}
