package team.backend.goWithMe.domain.trip.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.global.common.BaseTimeEntity;
import team.backend.goWithMe.domain.trip.vo.TripArrivalCount;
import team.backend.goWithMe.domain.trip.vo.TripArrivalImg;
import team.backend.goWithMe.domain.trip.vo.TripArrivalName;
import javax.persistence.*;


@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "trip")
public class Trip extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private Long id;

    // TODO arrival에서 arrivalName으로 수정함
    @Embedded
    private TripArrivalName arrivalName;

    @Embedded
    private TripArrivalCount arrivalCount;

    @Embedded
    private TripArrivalImg arrivalImg;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;

//    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
//    private List<TimeTable> timeTables = new ArrayList<>();

//    protected Trip(String tripName, String arrival,
//                   Long arrivalCount, String arrivalImg, Member member, @Nullable List<TimeTable> timeTables) {
//        this.tripName = tripName;
//        this.arrival = arrival;
//        this.arrivalCount = arrivalCount;
//        this.arrivalImg = arrivalImg;
//        this.member = member;
//        this.timeTables = timeTables;
//    }

    //===== 비즈니스 메서드 =====//

    public TripArrivalCount increaseArrivalCount() {
        return arrivalCount.addOne();
    }

    public void resetArrivalCount() {
        this.arrivalCount = TripArrivalCount.newArrivalCount();
    }

    public void changeArrivalName(TripArrivalName arrivalName) {
        this.arrivalName = arrivalName;
    }

    public void changeArrivalImg(TripArrivalImg tripArrivalImg) {
        this.arrivalImg = tripArrivalImg;
    }



//    public static Trip createTrip(String tripName, String arrival, Long arrivalCount,
//                                  String arrivalImg, Member member, @Nullable List<TimeTable> timeTables) {
//
//        return new Trip(tripName, arrival, arrivalCount, arrivalImg, member, timeTables);
//    }
}
