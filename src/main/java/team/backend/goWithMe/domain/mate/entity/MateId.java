package team.backend.goWithMe.domain.mate.entity;

import java.io.Serializable;

public class MateId implements Serializable {
    private int user;
    private int mateList;
    private int trip;

    public MateId() {}
    public MateId(int user, int mateList, int trip) {
        this.user = user;
        this.mateList = mateList;
        this.trip = trip;
    }
}
