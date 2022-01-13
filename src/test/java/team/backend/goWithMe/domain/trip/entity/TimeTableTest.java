package team.backend.goWithMe.domain.trip.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team.backend.goWithMe.domain.trip.exception.TimeTablePeriodValidException;
import team.backend.goWithMe.domain.trip.vo.TimeTableContent;
import team.backend.goWithMe.domain.trip.vo.TimeTableName;
import team.backend.goWithMe.domain.trip.vo.TimeTablePeriod;
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
        TimeTablePeriod timeTablePeriod = TimeTablePeriod.between(OLD_PERIOD_START, OLD_PERIOD_END);
        this.timeTable = TimeTable.createTimeTable(timeTableName, timeTableContent,
                timeTablePeriod);
    }

    @Test
    @DisplayName("시간표 객체 생성 예외 테스트")
    public void createTimeTableTest() throws Exception {
        // given
        TimeTableName timeTableName = TimeTableName.from("name1");
        TimeTableContent timeTableContent = TimeTableContent.from("content1");
        TimeTablePeriod timeTablePeriod = TimeTablePeriod.between(OLD_PERIOD_START, OLD_PERIOD_END);

        // when, Then
        assertThrows(NullPointerException.class, () ->
                TimeTable.createTimeTable(null, null, null));
        assertThrows(NullPointerException.class, () ->
                TimeTable.createTimeTable(timeTableName, null, null));
        assertThrows(NullPointerException.class, () ->
                TimeTable.createTimeTable(null, timeTableContent, null));
        assertThrows(NullPointerException.class, () ->
                TimeTable.createTimeTable(null, null, timeTablePeriod));
        assertThrows(NullPointerException.class, () ->
                TimeTable.createTimeTable(timeTableName, timeTableContent, null));
        assertThrows(NullPointerException.class, () ->
                TimeTable.createTimeTable(null, timeTableContent, timeTablePeriod));
        assertThrows(NullPointerException.class, () ->
                TimeTable.createTimeTable(timeTableName, null, timeTablePeriod));
        assertDoesNotThrow(() -> TimeTable.createTimeTable(timeTableName, timeTableContent, timeTablePeriod));
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
    @DisplayName("시간표 내용 초기화 테스트")
    public void resetTableContentTest() throws Exception {
        // given
        String oldContent = timeTable.getContent().tableContent();
        // when
        timeTable.resetTableContent();
        // then
        String resetContent = timeTable.getContent().tableContent();

        assertNotEquals(oldContent, resetContent);
        assertEquals(OLD_CONTENT, oldContent);
        assertEquals("", resetContent);
    }

    @Test
    @DisplayName("전체 일정 시작 지점 변경 테스트")
    public void changeTotalPeriodStartTest() throws Exception {
        // given
        LocalDateTime oldTotalStart = timeTable.getTotalPeriod().getStart();
        LocalDateTime newTimeStart = LocalDateTime.of(2022, 1, 2, 0, 0);
        // when
        TimeTablePeriod newPeriod = TimeTablePeriod.between(newTimeStart, OLD_PERIOD_END);
        timeTable.changeTablePeriod(newPeriod);

        // then
        LocalDateTime newTotalStart = timeTable.getTotalPeriod().getStart();
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
        assertThrows(TimeTablePeriodValidException.class, () ->
                timeTable.changeTablePeriod(TimeTablePeriod.between(dateLaterThanTotalEnd1, OLD_PERIOD_START)));
        assertThrows(TimeTablePeriodValidException.class, () ->
                timeTable.changeTablePeriod(TimeTablePeriod.between(dateLaterThanTotalEnd2, OLD_PERIOD_START)));
        assertThrows(TimeTablePeriodValidException.class, () ->
                timeTable.changeTablePeriod(TimeTablePeriod.between(dateLaterThanTotalEnd3, OLD_PERIOD_START)));
        assertThrows(TimeTablePeriodValidException.class, () ->
                timeTable.changeTablePeriod(TimeTablePeriod.between(dateLaterThanTotalEnd4, OLD_PERIOD_START)));

    }

    @Test
    @DisplayName("일정 끝 지점 변경 테스트")
    public void changeTotalPeriodEndTest() throws Exception {
        // given
        LocalDateTime oldTotalEnd = timeTable.getTotalPeriod().getEnd();
        LocalDateTime newTimeEnd = LocalDateTime.of(2022, 1, 11, 0, 0);

        // when
        TimeTablePeriod newPeriod = TimeTablePeriod.between(OLD_PERIOD_START, newTimeEnd);
        timeTable.changeTablePeriod(newPeriod);

        // then
        LocalDateTime newTotalEnd = timeTable.getTotalPeriod().getEnd();

        assertNotEquals(oldTotalEnd, newTotalEnd);
        assertEquals(OLD_PERIOD_END, oldTotalEnd);
        assertEquals(newTimeEnd, newTotalEnd);
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
        assertThrows(TimeTablePeriodValidException.class, () ->
                timeTable.changeTablePeriod(TimeTablePeriod.between(OLD_PERIOD_END, dateEarlierThanTotalStart1)));
        assertThrows(TimeTablePeriodValidException.class, () ->
                timeTable.changeTablePeriod(TimeTablePeriod.between(OLD_PERIOD_END, dateEarlierThanTotalStart2)));
        assertThrows(TimeTablePeriodValidException.class, () ->
                timeTable.changeTablePeriod(TimeTablePeriod.between(OLD_PERIOD_END, dateEarlierThanTotalStart3)));

    }

    @Test
    @DisplayName("일정 전체 변경 테스트")
    public void changeTotalPeriodTest() throws Exception {
        // given
        LocalDateTime newTimeStart = LocalDateTime.of(2022, 1, 9, 0, 0);
        LocalDateTime newTimeEnd = LocalDateTime.of(2022, 1, 31, 0, 0);

        // when
        TimeTablePeriod newPeriod = TimeTablePeriod.between(newTimeStart, newTimeEnd);
        timeTable.changeTablePeriod(newPeriod);

        // then
        assertEquals(newTimeStart, timeTable.getTotalPeriod().getStart());
        assertEquals(newTimeEnd, timeTable.getTotalPeriod().getEnd());
        assertEquals(
                Duration.between(newTimeStart, newTimeEnd),
                Duration.between( timeTable.getTotalPeriod().getStart(), timeTable.getTotalPeriod().getEnd())
        );
    }

    @Test
    @DisplayName("일정 시작지점보다 빠른 일정 끝지점, 오류 테스트")
    public void changeTotalPeriodToWrongTest1() throws Exception {
        // given
        LocalDateTime timeStart = LocalDateTime.of(2022, 1, 9, 0, 0);
        LocalDateTime timeEndEarlierThanTimeStart = LocalDateTime.of(2022, 1, 1, 0, 0);

        // when, than
        assertThrows(TimeTablePeriodValidException.class, () ->
                timeTable.changeTablePeriod(TimeTablePeriod.between(timeStart, timeEndEarlierThanTimeStart)));
    }

    @Test
    @DisplayName("일정 시작지점과 동일한 끝 지점, 오류 테스트")
    public void changeTotalPeriodToWrongTest2() throws Exception {
        // given
        LocalDateTime timeStart = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime timeEndEqualToTimeStart = LocalDateTime.of(2022, 1, 1, 0, 0);

        // when, than
        assertThrows(TimeTablePeriodValidException.class, () ->
                timeTable.changeTablePeriod(TimeTablePeriod.between(timeStart, timeEndEqualToTimeStart)));
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
                TimeTablePeriod.between(newPeriodStart, newPeriodEnd)
        );

        // then
        assertEquals(timeTable.getId(), timeTable.getId());
        assertNotEquals(OLD_NAME, timeTable.getTableName().arrivalName());
        assertNotEquals(OLD_CONTENT, timeTable.getContent().tableContent());
        assertNotEquals(OLD_PERIOD_START, timeTable.getTotalPeriod().getStart());
        assertNotEquals(OLD_PERIOD_END, timeTable.getTotalPeriod().getEnd());
        assertEquals(newName, timeTable.getTableName().arrivalName());
        assertEquals(newContent, timeTable.getContent().tableContent());
        assertEquals(newPeriodStart, timeTable.getTotalPeriod().getStart());
        assertEquals(newPeriodEnd, timeTable.getTotalPeriod().getEnd());
    }


}