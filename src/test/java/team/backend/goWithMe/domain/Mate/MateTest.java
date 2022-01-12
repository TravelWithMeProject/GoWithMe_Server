package team.backend.goWithMe.domain.Mate;

import org.junit.Test;

import org.junit.jupiter.api.DisplayName;

import team.backend.goWithMe.mate.domain.MateList;
import team.backend.goWithMe.mate.vo.MateEmail;
import team.backend.goWithMe.mate.vo.MateNickName;
import team.backend.goWithMe.mate.vo.MateProfileImg;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

        assertThat(mateList.getMateEmail()).isEqualTo(mateEmail);
        assertThat(mateList.getMateNickname()).isEqualTo(mateNickName);
        assertThat(mateList.getMateProfileImg()).isEqualTo(mateProfileImg);

    }

    @Test
    @DisplayName("친구 목록 변경 테스트")
    public void updateMateListTest() {

        final String value = "shin";
        final MateEmail mateEmail = MateEmail.from(value);

        final String nickname = "ssw";
        final MateNickName mateNickName = MateNickName.from(nickname);

        final String img = "dfdf";
        final MateProfileImg mateProfileImg = MateProfileImg.from(img);

        final MateList mateList = MateList.builder()
                .mateEmail(mateEmail)
                .mateNickName(mateNickName)
                .mateProfileImg(mateProfileImg)
                .build();

        String name1 = mateList.getMateNickname().mateNickname();
        String email1 = mateList.getMateEmail().mateEmail();
        String img1 = mateList.getMateProfileImg().mateProfileImg();

        final String value2 = "sang";
        final MateEmail mateEmail2 = MateEmail.from(value2);

        final String nickname2 = "aaa";
        final MateNickName mateNickName2 = MateNickName.from(nickname2);

        final String img2 = "zzzz";
        final MateProfileImg mateProfileImg2 = MateProfileImg.from(img2);

        mateList.update(mateEmail2, mateNickName2 , mateProfileImg2);

        String email2 = mateList.getMateEmail().mateEmail();
        String name2 = mateList.getMateNickname().mateNickname();
        String profileImg2 = mateList.getMateProfileImg().mateProfileImg();

        assertEquals(name1, "ssw");
        assertEquals(name2, "aaa");
        assertEquals(email1, "shin");
        assertEquals(email2, "sang");
        assertEquals(img1, "dfdf");
        assertEquals(profileImg2, "zzzz");
    }

}
