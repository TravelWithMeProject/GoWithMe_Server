package team.backend.goWithMe.domain.trip.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team.backend.goWithMe.domain.trip.vo.TimeTableContent;
import team.backend.goWithMe.domain.trip.vo.TimeTableName;
import team.backend.goWithMe.global.error.exception.InvalidValueException;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TimeTableTest {

    private TimeTable timeTable;
    final private String OLD_NAME = "전체 일정";
    final private String OLD_CONTENT = "일정은 이러이러 해~";
    final private LocalDateTime OLD_PERIOD_START = LocalDateTime.of(2022, 1, 1, 0, 0);
    final private LocalDateTime OLD_PERIOD_END = LocalDateTime.of(2022, 1, 10, 0, 0);

    @BeforeEach
    void init() {
        TimeTableName timeTableName = TimeTableName.from(OLD_NAME);
        TimeTableContent timeTableContent = TimeTableContent.from(OLD_CONTENT);
        this.timeTable = TimeTable.createTimeTable(timeTableName, timeTableContent,
                OLD_PERIOD_START, OLD_PERIOD_END);
    }

    @Test
    @DisplayName("기간 계산 테스트")
    public void ofDurationTest() throws Exception {
        // given init()

        // when
        Duration duration = timeTable.ofDuration();
        // then
        assertEquals(Duration.ofDays(9), duration);
        assertEquals(Duration.ofHours(9 * 24), duration);
        assertEquals(Duration.ofMinutes(9 * 24 * 60), duration);
    }

    @Test
    @DisplayName("시간표 명 변경 테스트")
    public void changeTimeTableNameTest() throws Exception {
        // given
        String oldTableName = timeTable.getTableName().arrivalName();

        // when
        timeTable.changeTableName(TimeTableName.from("일정2"));

        // then
        String newTableName = timeTable.getTableName().arrivalName();
        assertNotEquals(oldTableName, newTableName);
        assertEquals(OLD_NAME, oldTableName);
        assertEquals("일정2", newTableName);
    }

    @Test
    @DisplayName("시간표 명 null 로 변경 테스트")
    public void changeTimeTableNameToNullTest() throws Exception {
        // given
        // when, then
        assertThrows(InvalidValueException.class, () -> timeTable.changeTableName(TimeTableName.from(null)));
        assertThrows(InvalidValueException.class, () ->
                timeTable.changeTableName(TimeTableName.from(null)),
                "시간표 명은 필수입니다."
        );
    }

    @Test
    @DisplayName("시간표 명 \"\" 로 변경 테스트")
    public void changeTimeTableNameToBlankTest() throws Exception {
        // given
        // when, then
        assertThrows(InvalidValueException.class, () -> timeTable.changeTableName(TimeTableName.from("")));
    }

    @Test
    @DisplayName("시간표 내용 변경 테스트")
    public void changeTableContentTest() throws Exception {
        // given
        final String NEW_CONTENT = "새 일정은 이러이러해~";
        String oldContent = timeTable.getContent().tableContent();

        // when
        timeTable.changeTableContent(TimeTableContent.from(NEW_CONTENT));

        // then
        String newContent = timeTable.getContent().tableContent();

        assertNotEquals(oldContent, newContent);
        assertEquals(OLD_CONTENT, oldContent);
        assertEquals(NEW_CONTENT, newContent);
    }

    @Test
    @DisplayName("전체 일정 시작 지점 변경 테스트")
    public void changeTotalPeriodStartTest() throws Exception {
        // given
        LocalDateTime oldTotalStart = timeTable.getTotalStart();
        LocalDateTime newTime = LocalDateTime.of(2022, 1, 2, 0, 0);
        // when
        timeTable.changeTotalPeriodStart(newTime);

        // then
        LocalDateTime newTotalStart = timeTable.getTotalStart();
        assertNotEquals(oldTotalStart, newTotalStart);
        assertEquals(Duration.between(oldTotalStart, newTotalStart), Duration.ofDays(1));
        assertEquals(oldTotalStart, LocalDateTime.of(2022, 1, 1, 0, 0));
        assertEquals(newTotalStart, LocalDateTime.of(2022, 1, 2, 0, 0));
    }
    
    @Test
    @DisplayName("일정 끝 시점보다 늦는 날짜를 일정 시작 지점에 넣어보기")
    public void changeTotalPeriodStartToWrongTimeTest() throws Exception {
        // given
        LocalDateTime dateLaterThanTotalEnd1 = LocalDateTime.of(2022, 1, 11, 0, 0);
        LocalDateTime dateLaterThanTotalEnd2 = LocalDateTime.of(2022, 1, 10, 1, 0);
        LocalDateTime dateLaterThanTotalEnd3 = LocalDateTime.of(2022, 1, 10, 0, 1);
        LocalDateTime dateLaterThanTotalEnd4 = LocalDateTime.of(2023, 1, 10, 0, 0);

        // when, then
        assertThrows(InvalidValueException.class, () -> timeTable.changeTotalPeriodStart(dateLaterThanTotalEnd1));
        assertThrows(InvalidValueException.class, () -> timeTable.changeTotalPeriodStart(dateLaterThanTotalEnd2));
        assertThrows(InvalidValueException.class, () -> timeTable.changeTotalPeriodStart(dateLaterThanTotalEnd3));
        assertThrows(InvalidValueException.class, () -> timeTable.changeTotalPeriodStart(dateLaterThanTotalEnd4));
    }

    @Test
    public void changeTotalPeriodEndTest() throws Exception {
        // given
        LocalDateTime oldTotalEnd = timeTable.getTotalEnd();
        LocalDateTime newTime = LocalDateTime.of(2022, 1, 11, 0, 0);
        // when
        timeTable.changeTotalPeriodEnd(newTime);

        // then
        LocalDateTime newTotalEnd = timeTable.getTotalEnd();

        assertNotEquals(oldTotalEnd, newTotalEnd);
        assertEquals(OLD_PERIOD_END, oldTotalEnd);
        assertEquals(newTime, newTotalEnd);
        assertEquals(Duration.ofDays(1), Duration.between(oldTotalEnd, newTotalEnd));
    }

    @Test
    @DisplayName("일정 시작시점보다 빠른 날짜를 일정 끝시점에 넣어보기")
    public void changeTotalPeriodEndToWrongTest() throws Exception {
        // given
        LocalDateTime dateEarlierThanTotalStart1 = LocalDateTime.of(2021, 12, 31, 0, 0);
        LocalDateTime dateEarlierThanTotalStart2 = LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime dateEarlierThanTotalStart3 = LocalDateTime.of(2021, 12, 31, 23, 59);

        // when, then
        assertThrows(InvalidValueException.class, () -> timeTable.changeTotalPeriodEnd(dateEarlierThanTotalStart1));
        assertThrows(InvalidValueException.class, () -> timeTable.changeTotalPeriodEnd(dateEarlierThanTotalStart2));
        assertThrows(InvalidValueException.class, () -> timeTable.changeTotalPeriodEnd(dateEarlierThanTotalStart3));
    }

    @Test
    @DisplayName("일정 전체 변경 테스트")
    public void changeTotalPeriodTest() throws Exception {
        // given
        LocalDateTime newTimeStart = LocalDateTime.of(2022, 1, 9, 0, 0);
        LocalDateTime newTimeEnd = LocalDateTime.of(2022, 1, 31, 0, 0);

        // when
        timeTable.changeTotalPeriod(newTimeStart, newTimeEnd);

        // then
        assertEquals(newTimeStart, timeTable.getTotalStart());
        assertEquals(newTimeEnd, timeTable.getTotalEnd());
        assertEquals(Duration.between(newTimeStart, newTimeEnd),
                Duration.between(timeTable.getTotalStart(), timeTable.getTotalEnd()));
    }

    @Test
    @DisplayName("일정 시작지점보다 빠른 일정 끝지점, 오류 테스트")
    public void changeTotalPeriodToWrongTest1() throws Exception {
        // given
        LocalDateTime TimeStart = LocalDateTime.of(2022, 1, 9, 0, 0);
        LocalDateTime TimeEndEarlierThanTimeStart = LocalDateTime.of(2022, 1, 1, 0, 0);

        // when, than
        assertThrows(InvalidValueException.class, () -> timeTable.changeTotalPeriod(TimeStart, TimeEndEarlierThanTimeStart));
    }

    @Test
    @DisplayName("일정 시작지점과 동일한 끝 지점, 오류 테스트")
    public void changeTotalPeriodToWrongTest2() throws Exception {
        // given
        LocalDateTime TimeStart = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime TimeEndEqualToTimeStart = LocalDateTime.of(2022, 1, 1, 0, 0);

        // when, than
        assertThrows(InvalidValueException.class, () -> timeTable.changeTotalPeriod(TimeStart, TimeEndEqualToTimeStart));
    }

    @Test
    @DisplayName("시간표 변경 테스트")
    public void changeTimeTableTest() throws Exception {
        // given
        String newName = "새 일정 제목";
        String newContent = "새 일정 내용";
        LocalDateTime newPeriodStart = LocalDateTime.of(2022, 1, 2, 0, 0);
        LocalDateTime newPeriodEnd = LocalDateTime.of(2022, 1, 11, 0, 0);
        // when
        timeTable.changeTimeTable(
                TimeTableName.from(newName),
                TimeTableContent.from(newContent),
                newPeriodStart,
                newPeriodEnd
        );

        // then
        assertEquals(timeTable.getId(), timeTable.getId());
        assertNotEquals(OLD_NAME, timeTable.getTableName().arrivalName());
        assertNotEquals(OLD_CONTENT, timeTable.getContent().tableContent());
        assertNotEquals(OLD_PERIOD_START, timeTable.getTotalStart());
        assertNotEquals(OLD_PERIOD_END, timeTable.getTotalEnd());
        assertEquals(newName, timeTable.getTableName().arrivalName());
        assertEquals(newContent, timeTable.getContent().tableContent());
        assertEquals(newPeriodStart, timeTable.getTotalStart());
        assertEquals(newPeriodEnd, timeTable.getTotalEnd());
    }
}