package team.backend.goWithMe.domain.trip.entity;

import lombok.*;
import team.backend.goWithMe.domain.trip.vo.*;
import team.backend.goWithMe.global.common.BaseTimeEntity;

import javax.persistence.*;
import java.time.Duration;

@Entity @Getter
@Table(name = "schedule")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Schedule extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @Embedded
    private ScheduleTitle scheduleTitle;

    @Embedded
    private ScheduleContent scheduleContent;

    @Embedded
    private SchedulePeriod schedulePeriod;

    @Embedded
    private ScheduleCost scheduleCost;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private TimeTable timeTable;

    public static Schedule createSchedule(@NonNull TimeTable timeTable,
                                          @NonNull ScheduleTitle newScheduleTitle,
                                          @NonNull ScheduleContent newScheduleContent,
                                          @NonNull SchedulePeriod newSchedulePeriod,
                                          @NonNull ScheduleCost newScheduleCost) {
        return new Schedule(timeTable, newScheduleTitle, newScheduleContent, newSchedulePeriod, newScheduleCost);
    }

    private Schedule(TimeTable timeTable,
                     ScheduleTitle scheduleTitle,
                     ScheduleContent scheduleContent,
                     SchedulePeriod schedulePeriod,
                     ScheduleCost scheduleCost) {
        this.timeTable = timeTable;
        this.scheduleTitle = scheduleTitle;
        this.scheduleContent = scheduleContent;
        this.schedulePeriod = schedulePeriod;
        this.scheduleCost = scheduleCost;
    }


    //===== 비즈니스 메서드 =====//

    public Duration ofDuration() {
        return this.schedulePeriod.ofDuration();
    }

    public void changeSchedule(ScheduleTitle newScheduleTitle,
                               ScheduleContent newScheduleContent,
                               SchedulePeriod newSchedulePeriod,
                               ScheduleCost newScheduleCost) {
        changeScheduleTitle(newScheduleTitle);
        changeScheduleContent(newScheduleContent);
        changeSchedulePeriod(newSchedulePeriod);
        changeScheduleCost(newScheduleCost);
    }

    private void changeScheduleTitle(ScheduleTitle newScheduleTitle) {
        this.scheduleTitle = newScheduleTitle;
    }

    private void changeScheduleContent(ScheduleContent newScheduleContent) {
        this.scheduleContent = newScheduleContent;
    }

    private void changeSchedulePeriod(SchedulePeriod schedulePeriod) {
        this.schedulePeriod = this.schedulePeriod.changePeriod(schedulePeriod, timeTable.getTotalPeriod());
    }

    private void changeScheduleCost(ScheduleCost newScheduleCost) {
        this.scheduleCost = newScheduleCost;
    }

}
