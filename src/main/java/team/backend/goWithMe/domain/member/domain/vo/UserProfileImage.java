package team.backend.goWithMe.domain.member.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserProfileImage {

    @Column(name = "profile_image", nullable = false, length = 100)
    private String profileImage;

    public static UserProfileImage from(String profileImage) {
        return new UserProfileImage(profileImage);
    }

    @JsonValue
    public String userProfileImage() {
        return profileImage;
    }
}
