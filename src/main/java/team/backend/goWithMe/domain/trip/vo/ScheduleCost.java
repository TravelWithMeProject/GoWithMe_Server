package team.backend.goWithMe.domain.trip.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ScheduleCost {

    @Column(name = "schedule_cost")
    private Long amount;

    public static ScheduleCost wons(long amount) {
        return new ScheduleCost(amount);
    }

    public Long getAmount() {
        return this.amount;
    }

    public ScheduleCost plus(ScheduleCost amount) {
        return new ScheduleCost(this.amount += amount.getAmount());
    }

    public ScheduleCost minus(ScheduleCost amount) {
        return new ScheduleCost(this.amount -= amount.getAmount());
    }

    public ScheduleCost times(double ratio) {
        return new ScheduleCost(this.amount = (long)(this.amount * ratio));
    }

    public boolean isLessThan(ScheduleCost other) {
        return amount.compareTo(other.getAmount()) < 0;
    }

    public boolean isGreaterThan(ScheduleCost other) {
        return amount.compareTo(other.getAmount()) > 0;
    }

    public boolean isEqualTo(ScheduleCost other) {
        return amount.compareTo(other.getAmount()) == 0;
    }
}
