package team.backend.goWithMe.domain.trip.domain.persist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team.backend.goWithMe.domain.trip.error.exception.ScheduleTitleInvalidException;
import team.backend.goWithMe.domain.trip.domain.vo.*;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ScheduleTest {
    private Schedule schedule;
    private TimeTable timeTable;
    final private String OLD_NAME = "세부 일정";
    final private String OLD_CONTENT = "세부 일정은 이러이러 해~";
    final private LocalDateTime OLD_PERIOD_START = LocalDateTime.of(2022, 1, 1, 0, 0);
    final private LocalDateTime OLD_PERIOD_END = LocalDateTime.of(2022, 1, 10, 0, 0);
    final private Long OLD_COST = 10000L;

    @BeforeEach
    void init() {
        TimeTableName timeTableName = TimeTableName.from("전체 일정");
        TimeTableContent timeTableContent = TimeTableContent.from("전체 일정 내용입니다");
        TimeTablePeriod timeTablePeriod = TimeTablePeriod.between(OLD_PERIOD_START.minusDays(1L), OLD_PERIOD_END.plusDays(1L));
        this.timeTable = TimeTable.createTimeTable(timeTableName, timeTableContent, timeTablePeriod);

        ScheduleTitle title = ScheduleTitle.from(OLD_NAME);
        ScheduleContent content = ScheduleContent.from(OLD_CONTENT);
        ScheduleCost cost = ScheduleCost.wons(OLD_COST);
        SchedulePeriod period = SchedulePeriod.between(OLD_PERIOD_START, OLD_PERIOD_END, timeTable.getTotalPeriod());
        this.schedule = Schedule.createSchedule(timeTable, title, content, period, cost);
    }

    @Test
    @DisplayName("VO 객체 증명 테스트1 - 값이 같으면 같은 객체")
    public void voEqualsTest1() throws Exception {
        // given
        SchedulePeriod schedulePeriod = SchedulePeriod.between(OLD_PERIOD_START, OLD_PERIOD_END, timeTable.getTotalPeriod());
        ScheduleContent scheduleContent = ScheduleContent.from(OLD_CONTENT);
        ScheduleCost scheduleCost = ScheduleCost.wons(10000L);


        // when
        ScheduleTitle a = ScheduleTitle.from("AAA");
        ScheduleTitle b = ScheduleTitle.from("AAA");

        Schedule aSchedule = Schedule.createSchedule(timeTable, a, scheduleContent, schedulePeriod, scheduleCost);
        Schedule bSchedule = Schedule.createSchedule(timeTable, b, scheduleContent, schedulePeriod, scheduleCost);

        // then
        assertThat(aSchedule).isNotEqualTo(bSchedule);
        assertThat(a).isEqualTo(b);
        assertThat(aSchedule.getScheduleTitle()).isEqualTo(bSchedule.getScheduleTitle());
        assertThat(aSchedule.getScheduleContent()).isEqualTo(bSchedule.getScheduleContent());
        assertThat(aSchedule.getScheduleCost()).isEqualTo(bSchedule.getScheduleCost());
        assertThat(aSchedule.getSchedulePeriod()).isEqualTo(bSchedule.getSchedulePeriod());

    }

    @Test
    @DisplayName("VO 객체 증명 테스트2 - 값이 다르면 다른 객체")
    public void voEqualsTest2() throws Exception {
        // given
        SchedulePeriod schedulePeriod = SchedulePeriod.between(OLD_PERIOD_START, OLD_PERIOD_END, timeTable.getTotalPeriod());
        ScheduleContent scheduleContent = ScheduleContent.from(OLD_CONTENT);
        ScheduleCost scheduleCost = ScheduleCost.wons(10000L);


        // when
        ScheduleTitle a = ScheduleTitle.from("AAA");
        ScheduleTitle b = ScheduleTitle.from("XXX");

        Schedule aSchedule = Schedule.createSchedule(timeTable, a, scheduleContent, schedulePeriod, scheduleCost);
        Schedule bSchedule = Schedule.createSchedule(timeTable, b, scheduleContent, schedulePeriod, scheduleCost);

        // then
        assertThat(aSchedule).isNotEqualTo(bSchedule);
        assertThat(a).isNotEqualTo(b);
    }

    @Test
    @DisplayName("세부 일정 생성 예외 테스트")
    public void createScheduleTest() throws Exception {
        // given
        ScheduleTitle title = ScheduleTitle.from("title");
        ScheduleContent content = ScheduleContent.from("content");
        SchedulePeriod period = SchedulePeriod.between(OLD_PERIOD_START, OLD_PERIOD_END, timeTable.getTotalPeriod());
        ScheduleCost scheduleCost = ScheduleCost.wons(10000L);

        // when, then
        assertThrows(NullPointerException.class, () ->
                Schedule.createSchedule(null, null, null, null, null));
        assertThrows(NullPointerException.class, () ->
                Schedule.createSchedule(timeTable, null, null, null, null));
        assertThrows(NullPointerException.class, () ->
                Schedule.createSchedule(timeTable, title, null, null, null));
        assertDoesNotThrow(() -> Schedule.createSchedule(timeTable, title, content, period, scheduleCost));
    }

    @Test
    public void ofDurationTest() throws Exception {
        // given

        // when
        Duration duration = schedule.ofDuration();

        // then
        assertThat(duration).isEqualTo(Duration.ofDays(9));
        assertEquals(Duration.ofHours(9 * 24), duration);
        assertEquals(Duration.ofMinutes(9 * 24 * 60), duration);
    }

    @Test
    @DisplayName("세부 일정 제목 변경 테스트")
    public void changeScheduleTitleTest() throws Exception {
        // given
        ScheduleTitle oldScheduleTitle = schedule.getScheduleTitle();

        // when
        ScheduleTitle newScheduleTitle = ScheduleTitle.from("새 제목");
        schedule.changeSchedule(newScheduleTitle, schedule.getScheduleContent(),
                schedule.getSchedulePeriod(), schedule.getScheduleCost());

        // then
        assertThat(newScheduleTitle).isNotEqualTo(oldScheduleTitle);
        assertThat(oldScheduleTitle.scheduleTitle()).isEqualTo(OLD_NAME);
        assertThat(newScheduleTitle.scheduleTitle()).isEqualTo("새 제목");
    }

    @Test
    @DisplayName("세부 일정 제목 null로 변경 테스트")
    public void changeScheduleTitleToNullTest() throws Exception {
        // when, then
        assertThrows(ScheduleTitleInvalidException.class, () ->
                schedule.changeSchedule(ScheduleTitle.from(null), schedule.getScheduleContent(),
                        schedule.getSchedulePeriod(), schedule.getScheduleCost()));
    }

    @Test
    @DisplayName("세부 일정 제목 \"\" 로 변경 테스트")
    public void changeTimeTableNameToBlankTest() throws Exception {
        // given
        // when, then
        assertThrows(ScheduleTitleInvalidException.class, () ->
                schedule.changeSchedule(ScheduleTitle.from(""), schedule.getScheduleContent(),
                        schedule.getSchedulePeriod(), schedule.getScheduleCost()));
    }

    @Test
    @DisplayName("세부 일정 내용 변경 테스트")
    public void changeScheduleContentTest() throws Exception {
        // given
        ScheduleContent oldContent = schedule.getScheduleContent();
        // when
        schedule.changeSchedule(schedule.getScheduleTitle(), ScheduleContent.from("새 일정 내용"),
                schedule.getSchedulePeriod(), schedule.getScheduleCost());

        // then
        ScheduleContent newContent = schedule.getScheduleContent();
        assertThat(newContent).isNotEqualTo(oldContent);
        assertThat(oldContent.scheduleContent()).isEqualTo(OLD_CONTENT);
        assertThat(newContent.scheduleContent()).isEqualTo("새 일정 내용");
    }

    @Test
    @DisplayName("세부 일정 시작 지점 변경 테스트")
    public void changeDetailPeriodStartTest() throws Exception {
        // given
        SchedulePeriod oldPeriod = schedule.getSchedulePeriod();
        LocalDateTime newTimeStart = LocalDateTime.of(2022, 1, 2, 0, 0);
        // when
        SchedulePeriod newSchedulePeriod = SchedulePeriod.between(newTimeStart,
                schedule.getSchedulePeriod().scheduleDetailEnd(),
                schedule.getTimeTable().getTotalPeriod());
        schedule.changeSchedule(schedule.getScheduleTitle(), schedule.getScheduleContent(), newSchedulePeriod, schedule.getScheduleCost());

        // then
        SchedulePeriod newPeriod = schedule.getSchedulePeriod();
        assertThat(newPeriod).isNotEqualTo(oldPeriod);
        assertThat(newPeriod.scheduleDetailStart()).isEqualTo(newTimeStart);
        assertThat(oldPeriod.scheduleDetailStart()).isEqualTo(OLD_PERIOD_START);
        assertThat(Duration.between(oldPeriod.scheduleDetailStart(), newPeriod.scheduleDetailStart())).isEqualTo(Duration.ofDays(1));

    }

    @Test
    @DisplayName("세부 일정 끝 지점 변경 테스트")
    public void changeDetailPeriodEndTest() throws Exception {
        // given
        SchedulePeriod oldPeriod = schedule.getSchedulePeriod();
        LocalDateTime newTimeEnd = OLD_PERIOD_END.plusDays(1);
        // when
        SchedulePeriod newSchedulePeriod = SchedulePeriod.between(
                schedule.getSchedulePeriod().scheduleDetailStart(),
                newTimeEnd,
                schedule.getTimeTable().getTotalPeriod());
        schedule.changeSchedule(schedule.getScheduleTitle(), schedule.getScheduleContent(), newSchedulePeriod, schedule.getScheduleCost());

        // then
        SchedulePeriod newPeriod = schedule.getSchedulePeriod();
        assertThat(newPeriod).isNotEqualTo(oldPeriod);
        assertThat(newPeriod.scheduleDetailEnd()).isEqualTo(newTimeEnd);
        assertThat(oldPeriod.scheduleDetailEnd()).isEqualTo(OLD_PERIOD_END);
        assertThat(Duration.between(oldPeriod.scheduleDetailEnd(), newPeriod.scheduleDetailEnd())).isEqualTo(Duration.ofDays(1));

    }

    @Test
    @DisplayName("세부 일정 비용 변경 테스트")
    public void changeScheduleCostTest() throws Exception {
        // given
        ScheduleCost oldCost = schedule.getScheduleCost();
        // when
        ScheduleCost newScheduleCost = ScheduleCost.wons(1000L);
        schedule.changeSchedule(
                schedule.getScheduleTitle(),
                schedule.getScheduleContent(),
                schedule.getSchedulePeriod(),
                newScheduleCost
        );

        // then
        ScheduleCost newCost = schedule.getScheduleCost();
        assertThat(newCost).isNotEqualTo(oldCost);
        assertThat(newCost.amount()).isEqualTo(1000L);
        assertThat(oldCost.amount()).isEqualTo(OLD_COST);
    }


}