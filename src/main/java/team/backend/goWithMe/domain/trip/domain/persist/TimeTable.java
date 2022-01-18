package team.backend.goWithMe.domain.trip.domain.persist;

import lombok.*;
import team.backend.goWithMe.domain.trip.domain.vo.TimeTableContent;
import team.backend.goWithMe.domain.trip.domain.vo.TimeTableName;
import team.backend.goWithMe.domain.trip.domain.vo.TimeTablePeriod;
import team.backend.goWithMe.global.common.BaseTimeEntity;

import javax.persistence.*;
import java.time.Duration;

@Entity @Getter
@Table(name = "time_table")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeTable extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "table_id")
    private Long id;

    @Embedded
    private TimeTableName tableName;

    @Embedded
    private TimeTableContent content;

    @Embedded
    private TimeTablePeriod totalPeriod;

//    @OneToMany(mappedBy = "timeTable", cascade = CascadeType.ALL) // member에 의해 maepped됐을 뿐이라는 표시
//    private List<Schedule> schedules = new ArrayList<>();

    public static TimeTable createTimeTable(@NonNull TimeTableName tableName,
                                            @NonNull TimeTableContent content,
                                            @NonNull TimeTablePeriod totalPeriod) {
        return new TimeTable(tableName, content, totalPeriod);
    }

    private TimeTable(TimeTableName tableName, TimeTableContent content,
                      TimeTablePeriod totalPeriod) {
        this.tableName = tableName;
        this.content = content;
        this.totalPeriod = totalPeriod;
    }

    //===== 비즈니스 메서드 =====//
    public void changeTimeTable(TimeTableName name, TimeTableContent content, TimeTablePeriod timeTablePeriod) {
        changeTableName(name);
        changeTableContent(content);
        changeTablePeriod(timeTablePeriod);
    }

    public Duration ofDuration() {
        return this.totalPeriod.ofDuration();
    }

    public void resetTableContent() {
        this.content = this.content.resetContent();
    }

    private void changeTableName(TimeTableName timeTableName) {
        this.tableName = timeTableName;
    }

    private void changeTableContent(TimeTableContent timeTableContent) {
        this.content = timeTableContent;
    }

    private void changeTablePeriod(TimeTablePeriod timeTablePeriod) {
        this.totalPeriod.changePeriod(timeTablePeriod);
    }
}
