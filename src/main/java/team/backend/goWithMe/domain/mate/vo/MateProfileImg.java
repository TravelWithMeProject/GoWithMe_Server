package team.backend.goWithMe.domain.mate.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class MateProfileImg {

    @Column(name = "profile_url", nullable = false)
    private String profileImage;

    public static MateProfileImg from(String profileImage) {
        return new MateProfileImg(profileImage);
    }
    @JsonValue
    public String mateProfileImg() {
        return profileImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MateProfileImg that = (MateProfileImg) o;
        return Objects.equals(mateProfileImg(), that.profileImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mateProfileImg());
    }
}
