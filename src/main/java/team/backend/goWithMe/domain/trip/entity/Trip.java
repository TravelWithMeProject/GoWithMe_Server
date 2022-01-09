package team.backend.goWithMe.domain.trip.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.global.common.BaseTimeEntity;
import team.backend.goWithMe.domain.trip.vo.TripArrivalCount;
import team.backend.goWithMe.domain.trip.vo.TripArrivalImg;
import team.backend.goWithMe.domain.trip.vo.TripArrivalName;
import javax.persistence.*;


@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "trip")
public class Trip extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private Long id;

    // TODO arrival에서 arrivalName으로 수정함
    @Embedded
    @Column(name = "arrival_name")
    private TripArrivalName arrivalName;

    @Embedded
    @Column(name = "arrival_count")
    private TripArrivalCount arrivalCount;

    @Embedded
    @Column(name = "arrival_img")
    private TripArrivalImg arrivalImg;

    private Trip(TripArrivalName arrivalName, TripArrivalCount arrivalCount, TripArrivalImg arrivalImg) {
        this.arrivalName = arrivalName;
        this.arrivalCount = arrivalCount;
        this.arrivalImg = arrivalImg;
    }

    public static Trip createTrip(TripArrivalName arrivalName, TripArrivalCount arrivalCount, TripArrivalImg arrivalImg) {
        return new Trip(arrivalName, arrivalCount, arrivalImg);
    }

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;

//    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
//    private List<TimeTable> timeTables = new ArrayList<>();


    //===== 비즈니스 메서드 =====//

    public void increaseArrivalCount() {
        this.arrivalCount.addOne();
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
