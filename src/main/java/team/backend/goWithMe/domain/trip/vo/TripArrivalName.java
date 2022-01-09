package team.backend.goWithMe.domain.trip.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.global.error.exception.InvalidValueException;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TripArrivalName {

    @NotBlank
    private String name;

    public static TripArrivalName from(String arrivalName) {
        validateTripArrivalName(arrivalName);
        return new TripArrivalName(arrivalName);
    }

    @JsonValue
    public String arrivalName() {
        return this.name;
    }

    private static void validateTripArrivalName(String arrivalName) {
        if (arrivalName == null || arrivalName.isBlank()) {
            throw new InvalidValueException("여행지 명은 필수입니다.");
        }
    }
}
