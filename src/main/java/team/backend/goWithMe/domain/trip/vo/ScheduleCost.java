package team.backend.goWithMe.domain.trip.vo;

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
public final class ScheduleCost {

    @Column(name = "schedule_cost")
    private Long amount;

    public static ScheduleCost wons(long amount) {
        return new ScheduleCost(amount);
    }

    @JsonValue
    public Long amount() {
        return this.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ScheduleCost))
            return false;
        ScheduleCost cost = (ScheduleCost)o;
        return amount().equals(cost.amount);
    }

    public ScheduleCost plus(ScheduleCost amount) {
        return new ScheduleCost(this.amount += amount.amount());
    }

    public ScheduleCost minus(ScheduleCost amount) {
        return new ScheduleCost(this.amount -= amount.amount());
    }

    public ScheduleCost times(double ratio) {
        return new ScheduleCost(this.amount = (long)(this.amount * ratio));
    }

    public boolean isLessThan(ScheduleCost other) {
        return amount.compareTo(other.amount()) < 0;
    }

    public boolean isGreaterThan(ScheduleCost other) {
        return amount.compareTo(other.amount()) > 0;
    }

    public boolean isEqualTo(ScheduleCost other) {
        return amount.compareTo(other.amount()) == 0;
    }
}
