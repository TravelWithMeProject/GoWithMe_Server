package team.backend.goWithMe.mate.domain;

import io.jsonwebtoken.lang.Assert;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.mate.vo.MateEmail;
import team.backend.goWithMe.mate.vo.MateNickName;
import team.backend.goWithMe.mate.vo.MateProfileImg;

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
        Assert.hasText(mateEmail.mateEmail(), "mateEmail must not be empty");
        Assert.hasText(mateNickName.mateNickname(), "mateNickName must not be empty");
        Assert.hasText(mateProfileImg.mateProfileImg(),"mateProfileImg must not be empty");
        this.mateEmail = mateEmail;
        this.mateNickname = mateNickName;
        this.mateProfileImg = mateProfileImg;
    }
//    @OneToMany(mappedBy = "mateList")
//    private List<Mate> team.backend.goWithMe.mate = new ArrayList<>();

    /*비즈니스 메서드*/

    private void changeEmail(MateEmail mateEmail) {
        this.mateEmail = mateEmail;
    }

    private void changeNickName(MateNickName mateNickName) {
        this.mateNickname = mateNickName;
    }

    private void changeMateProfileImg(MateProfileImg mateProfileImg) {
        this.mateProfileImg = mateProfileImg;
    }

    public void update(MateEmail mateEmail, MateNickName mateNickName, MateProfileImg mateProfileImg) {
        changeEmail(mateEmail);
        changeNickName(mateNickName);
        changeMateProfileImg(mateProfileImg);
    }
}
