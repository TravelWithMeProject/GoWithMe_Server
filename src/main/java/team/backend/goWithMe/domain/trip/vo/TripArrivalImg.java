package team.backend.goWithMe.domain.trip.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TripArrivalImg {

    @NotEmpty
    private String arrivalImgUrl;

    public TripArrivalImg from(String arrivalImgUrl) {
        String filteredImgUrl = filterImgUrl(arrivalImgUrl);
        return new TripArrivalImg(filteredImgUrl);
    }

    @JsonValue
    public String arrivalImg() {
        return arrivalImgUrl;
    }

    private String filterImgUrl(String arrivalImgUrl) {
        if (arrivalImgUrl == null || arrivalImgUrl.isBlank()) {
            return "기본 이미지 URL";
        }
        return arrivalImgUrl;
    }

}
