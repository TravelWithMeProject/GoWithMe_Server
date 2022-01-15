package team.backend.goWithMe.domain.trip.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TripArrivalCount {

    @NotNull
    @Column(name = "arrival_count")
    private Long arrivalCount;

    public static TripArrivalCount newArrivalCount() {
        return new TripArrivalCount(0L);
    }

    @JsonValue
    public Long arrivalCount() {
        return this.arrivalCount;
    }

    public void addOne() {
        this.arrivalCount += 1L;
    }
}
