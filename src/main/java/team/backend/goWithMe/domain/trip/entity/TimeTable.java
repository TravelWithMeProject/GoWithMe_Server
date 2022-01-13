package team.backend.goWithMe.domain.trip.entity;

import lombok.*;
import team.backend.goWithMe.domain.trip.vo.TimeTableContent;
import team.backend.goWithMe.domain.trip.vo.TimeTableName;
import team.backend.goWithMe.domain.trip.vo.TimeTablePeriod;
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

    private TimeTable(TimeTableName tableName, TimeTableContent content,
                      TimeTablePeriod totalPeriod) {
        this.tableName = tableName;
        this.content = content;
        this.totalPeriod = totalPeriod;
    }

    public static TimeTable createTimeTable(@NonNull TimeTableName tableName,
                                            @NonNull TimeTableContent content,
                                            @NonNull TimeTablePeriod totalPeriod) {
        return new TimeTable(tableName, content, totalPeriod);
    }

    //===== 비즈니스 메서드 =====//

    public Duration ofDuration() {
        return this.totalPeriod.ofDuration();
    }

    public void changeTableName(TimeTableName timeTableName) {
        this.tableName = timeTableName;
    }

    public void changeTableContent(TimeTableContent timeTableContent) {
        this.content = timeTableContent;
    }

    public void resetTableContent() {
        this.content.resetContent();
    }

    public void changeTablePeriod(TimeTablePeriod timeTablePeriod) {
        this.totalPeriod.changePeriod(timeTablePeriod.getStart(), timeTablePeriod.getEnd());
    }

    public void changeTimeTable(TimeTableName name, TimeTableContent content, TimeTablePeriod timeTablePeriod) {
        changeTableName(name);
        changeTableContent(content);
        changeTablePeriod(timeTablePeriod);
    }

}
