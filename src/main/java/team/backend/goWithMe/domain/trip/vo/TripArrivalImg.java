package team.backend.goWithMe.domain.trip.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TripArrivalImg {

    @NotBlank
    private String arrivalImgUrl;

    public static TripArrivalImg from(String arrivalImgUrl) {
        return new TripArrivalImg(arrivalImgUrl);
    }

    @JsonValue
    public String arrivalImg() {
        return this.arrivalImgUrl;
    }

}
