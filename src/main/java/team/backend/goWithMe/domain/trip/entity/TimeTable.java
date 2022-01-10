package team.backend.goWithMe.domain.trip.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.domain.trip.vo.TimeTableContent;
import team.backend.goWithMe.domain.trip.vo.TimeTableName;
import team.backend.goWithMe.global.common.BaseTimeEntity;
import team.backend.goWithMe.global.error.exception.InvalidValueException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;

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

    @NotNull
    @Column(name = "total_start")
    private LocalDateTime totalStart;

    @NotNull
    @Column(name = "total_end")
    private LocalDateTime totalEnd;

    private TimeTable(TimeTableName tableName, TimeTableContent content,
                     LocalDateTime totalStart, LocalDateTime totalEnd) {
        this.tableName = tableName;
        this.content = content;
        this.totalStart = totalStart;
        this.totalEnd = totalEnd;
    }

    public static TimeTable createTimeTable(TimeTableName tableName, TimeTableContent content,
                                            LocalDateTime totalStart, LocalDateTime totalEnd) {
        validateTotalPeriod(totalStart, totalEnd);
        return new TimeTable(tableName, content, totalStart, totalEnd);
    }

    //===== 비즈니스 메서드 =====//

    public Duration ofDuration() {
        return Duration.between(this.totalStart, this.totalEnd);
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

    public void changeTotalPeriodStart(LocalDateTime newTotalStart) {
        validateTotalPeriod(newTotalStart, this.totalEnd);
        this.totalStart = newTotalStart;
    }

    public void changeTotalPeriodEnd(LocalDateTime newTotalEnd) {
        validateTotalPeriod(this.totalStart, newTotalEnd);
        this.totalEnd = newTotalEnd;
    }

    public void changeTotalPeriod(LocalDateTime newTotalStart, LocalDateTime newTotalEnd) {
        validateTotalPeriod(newTotalStart, newTotalEnd);
        this.totalStart = newTotalStart;
        this.totalEnd = newTotalEnd;
    }

    public void changeTimeTable(TimeTableName name, TimeTableContent content,
                                LocalDateTime periodStart, LocalDateTime periodEnd) {
        changeTableName(name);
        changeTableContent(content);
        changeTotalPeriod(periodStart, periodEnd);
    }

    private static void validateTotalPeriod(LocalDateTime totalStart, LocalDateTime totalEnd) {
        if (totalStart.isAfter(totalEnd)) {
            throw new InvalidValueException("전체일정 시작 시점과 끝 시점이 반대입니다.");
        }
        if (totalStart.isEqual(totalEnd)) {
            throw new InvalidValueException("전체일정이 0(끝지점과 시작지점이 같음)이 될 수 없습니다.");
        }
    }

}
