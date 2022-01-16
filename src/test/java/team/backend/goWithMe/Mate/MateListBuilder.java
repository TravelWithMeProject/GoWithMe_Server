package team.backend.goWithMe.Mate;

import team.backend.goWithMe.domain.mate.domain.MateList;
import team.backend.goWithMe.domain.mate.vo.MateEmail;
import team.backend.goWithMe.domain.mate.vo.MateNickName;
import team.backend.goWithMe.domain.mate.vo.MateProfileImg;

public class MateListBuilder {

    public static MateList build() {
        final String value = "shinsw6455@naver.com";
        final MateEmail mateEmail = MateEmail.from(value);
        final MateNickName mateNickName = MateNickName.from("ssw");
        final MateProfileImg mateProfileImg = MateProfileImg.from("dfdf");
        return createMateList(mateEmail, mateNickName, mateProfileImg);
    }

    public static MateList build(MateEmail mateEmail, MateNickName mateNickName, MateProfileImg mateProfileImg) {
        return createMateList(mateEmail, mateNickName, mateProfileImg);
    }

    private static MateList createMateList(MateEmail mateEmail, MateNickName mateNickName, MateProfileImg mateProfileImg) {
        return MateList.builder()
                .mateEmail(mateEmail)
                .mateNickName(mateNickName)
                .mateProfileImg(mateProfileImg)
                .build();
    }
}