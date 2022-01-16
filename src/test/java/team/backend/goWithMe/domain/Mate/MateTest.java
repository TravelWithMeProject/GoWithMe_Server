package team.backend.goWithMe.domain.Mate;

import org.junit.Test;

import org.junit.jupiter.api.DisplayName;

import team.backend.goWithMe.domain.mate.domain.persist.MateList;
import team.backend.goWithMe.domain.mate.domain.vo.MateEmail;
import team.backend.goWithMe.domain.mate.domain.vo.MateNickName;
import team.backend.goWithMe.domain.mate.domain.vo.MateProfileImg;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MateTest {

    @Test
    @DisplayName("친구목록 빌더 테스트")
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
    @DisplayName("친구 목록 빌더 실패 테스트")
    public void buildFailTest() {

        final String value = "shinsw6455@naver.com";
        final MateEmail mateEmail = MateEmail.from(value);

        final String img = "dfdf";
        final MateProfileImg mateProfileImg = MateProfileImg.from(img);

        assertThrows(NullPointerException.class, () -> {
            MateList mateList = MateList.builder()
                    .mateEmail(mateEmail)
                    .mateProfileImg(mateProfileImg)
                    .build();
        });

    }

    @Test
    @DisplayName("친구 목록 변경 테스트")
    public void updateMateListTest() {

        final MateList mateList = MateListBuilder.build();

        final String value2 = "sang";
        final MateEmail mateEmail2 = MateEmail.from(value2);

        final String nickname2 = "aaa";
        final MateNickName mateNickName2 = MateNickName.from(nickname2);

        final String img2 = "zzzz";
        final MateProfileImg mateProfileImg2 = MateProfileImg.from(img2);

        mateList.updateMateList(mateEmail2, mateNickName2 , mateProfileImg2);

        assertThat(mateList.getMateEmail()).isEqualTo(mateEmail2);
        assertThat(mateList.getMateNickname()).isEqualTo(mateNickName2);
        assertThat(mateList.getMateProfileImg()).isEqualTo(mateProfileImg2);
    }

    @Test
    @DisplayName("객체 equals 비교 테스트")
    public void equalsTest() {

        final String value = "shin@naver.com";
        final MateEmail mateEmail1 = MateEmail.from(value);
        final MateEmail mateEmail2 = MateEmail.from(value);

        final String nickname = "ssw";
        final MateNickName mateNickName1 = MateNickName.from(nickname);
        final MateNickName mateNickName2 = MateNickName.from(nickname);

        final String img = "dfdf";
        final MateProfileImg mateProfileImg1 = MateProfileImg.from(img);
        final MateProfileImg mateProfileImg2 = MateProfileImg.from(img);

        assertThat(mateEmail1).isEqualTo(mateEmail2);
        assertThat(mateNickName1).isEqualTo(mateNickName2);
        assertThat(mateProfileImg1).isEqualTo(mateProfileImg2);
    }
}
