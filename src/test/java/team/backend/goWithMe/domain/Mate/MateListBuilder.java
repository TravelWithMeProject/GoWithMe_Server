package team.backend.goWithMe.domain.Mate;

import team.backend.goWithMe.mate.domain.MateList;
import team.backend.goWithMe.mate.vo.MateEmail;
import team.backend.goWithMe.mate.vo.MateNickName;
import team.backend.goWithMe.mate.vo.MateProfileImg;

public class MateListBuilder {

    public static MateList build() {
        final MateEmail mateEmail = MateEmail.from("shinsw6455@naver.com");
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
