package team.backend.goWithMe.domain.member.domain.vo;

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
public final class UserProfileImage {

    @Column(name = "profile_image", nullable = false, length = 100)
    private String profileImage;

    public static UserProfileImage from(final String profileImage) {
        return new UserProfileImage(profileImage);
    }

    @JsonValue
    public String userProfileImage() {
        return profileImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfileImage profile = (UserProfileImage) o;
        return Objects.equals(userProfileImage(), profile.profileImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userProfileImage());
    }
}
