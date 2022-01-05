package team.backend.goWithMe.domain.trip.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TripArrivalCount {

    private Long arrivalCount;

    public static TripArrivalCount newArrivalCount() {
        return new TripArrivalCount(0L);
    }

    @JsonValue
    public Long arrivalCount() {
        return arrivalCount;
    }

    public TripArrivalCount addOne() {
        arrivalCount += 1;
        return new TripArrivalCount(arrivalCount);
    }
}
