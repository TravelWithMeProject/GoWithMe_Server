package team.backend.goWithMe.domain.trip.domain.persist;

import lombok.*;
import team.backend.goWithMe.domain.member.domain.persist.Member;
import team.backend.goWithMe.domain.trip.domain.vo.TimeTableContent;
import team.backend.goWithMe.domain.trip.domain.vo.TimeTableName;
import team.backend.goWithMe.domain.trip.domain.vo.TimeTablePeriod;
import team.backend.goWithMe.global.common.BaseTimeEntity;

import javax.persistence.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

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
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member member;

    @OneToMany(mappedBy = "timeTable", cascade = CascadeType.ALL) // member에 의해 maepped됐을 뿐이라는 표시
    private List<Schedule> schedules = new ArrayList<>();

    public static TimeTable createTimeTable(@NonNull TimeTableName tableName,
                                            @NonNull TimeTableContent content,
                                            @NonNull TimeTablePeriod totalPeriod,
                                            @NonNull Member member) {
            return new TimeTable(tableName, content, totalPeriod, member);
        }

    private TimeTable(TimeTableName tableName, TimeTableContent content,
                TimeTablePeriod totalPeriod,Member member) {
                this.tableName = tableName;
                this.content = content;
                this.totalPeriod = totalPeriod;
                this.member = member;
            }

            //===== 비즈니스 메서드 =====//
            public void changeTimeTable(TimeTable timeTable) {
                changeTableName(timeTable.getTableName());
                changeTableContent(timeTable.getContent());
                changeTablePeriod(timeTable.getTotalPeriod());
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
