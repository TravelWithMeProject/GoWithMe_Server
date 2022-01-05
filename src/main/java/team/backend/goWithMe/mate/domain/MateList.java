package team.backend.goWithMe.mate.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.mate.vo.MateEmail;
import team.backend.goWithMe.mate.vo.MateNickName;
import team.backend.goWithMe.mate.vo.MateProfileImg;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "mateList")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MateList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mate_id")
    private Long id;

    @Embedded
    private MateEmail email;

    @Embedded
    private MateNickName mateNickname;

    @Embedded
    private MateProfileImg mateProfileImg;

    @Embedded
    private LocalDateTime birth;

//    @OneToMany(mappedBy = "mateList")
//    private List<Mate> team.backend.goWithMe.mate = new ArrayList<>();

    /*비즈니스 메서드*/
    public void changeEmail(MateEmail email) {
        this.email = email;
    }

    public void changeNickName(MateNickName mateNickName) {
        this.mateNickname = mateNickName;
    }

    public void changeMateProfileImg(MateProfileImg mateProfileImg) {
        this.mateProfileImg = mateProfileImg;
    }

}
