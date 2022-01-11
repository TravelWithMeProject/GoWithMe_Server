package team.backend.goWithMe.mate.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MateProfileImg {

    @Column(name = "profile_url", nullable = false)
    private String profileImage;

    public static MateProfileImg from(String profileImage) {
        return new MateProfileImg(profileImage);
    }
    @JsonValue
    public String getMateProfileImg() {
        return profileImage;
    }
}
