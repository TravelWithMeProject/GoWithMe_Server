package team.backend.goWithMe.domain.favorite.domain.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Accommodation {

    @NotNull(message = "null은 안됩니다.")
    @Column(nullable = false, length = 50)
    private String accommodation;

    public static Accommodation from(final String accommodation) {
        return new Accommodation(accommodation);
    }

    public String accommodation() {
        return accommodation;
    }
}
