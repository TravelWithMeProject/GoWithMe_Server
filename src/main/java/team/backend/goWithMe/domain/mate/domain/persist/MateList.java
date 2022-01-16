package team.backend.goWithMe.domain.mate.domain.persist;

import io.jsonwebtoken.lang.Assert;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.domain.mate.domain.vo.MateEmail;
import team.backend.goWithMe.domain.mate.domain.vo.MateNickName;
import team.backend.goWithMe.domain.mate.domain.vo.MateProfileImg;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "mate_list")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MateList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mate_id")
    private Long id;

    @Embedded
    private MateEmail mateEmail;

    @Embedded
    private MateNickName mateNickname;

    @Embedded
    private MateProfileImg mateProfileImg;

    @Embedded
    private LocalDateTime birth;

    @Builder
    public MateList(MateEmail mateEmail, MateNickName mateNickName, MateProfileImg mateProfileImg) {
        Assert.hasText(mateEmail.mateEmail(),"이메일이 없습니다.");
        Assert.hasText(mateNickName.mateNickname(), "닉네임이 없습니다.");
        Assert.hasText(mateProfileImg.mateProfileImg(),"프로필 이미지가 없습니다.");
        this.mateEmail = mateEmail;
        this.mateNickname = mateNickName;
        this.mateProfileImg = mateProfileImg;
    }
//    @OneToMany(mappedBy = "mateList")
//    private List<Mate> team.backend.goWithMe.domain.mate = new ArrayList<>();

    /*비즈니스 메서드*/

    public void updateMateList(MateEmail mateEmail, MateNickName mateNickName, MateProfileImg mateProfileImg) {
        changeEmail(mateEmail);
        changeNickName(mateNickName);
        changeMateProfileImg(mateProfileImg);
    }

    private void changeEmail(MateEmail mateEmail) {
        this.mateEmail = mateEmail;
    }

    private void changeNickName(MateNickName mateNickName) {
        this.mateNickname = mateNickName;
    }

    private void changeMateProfileImg(MateProfileImg mateProfileImg) {
        this.mateProfileImg = mateProfileImg;
    }


}
