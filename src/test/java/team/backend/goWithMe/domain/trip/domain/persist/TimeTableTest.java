package team.backend.goWithMe.domain.trip.domain.persist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team.backend.goWithMe.domain.member.domain.persist.Member;
import team.backend.goWithMe.domain.member.domain.persist.RoleType;
import team.backend.goWithMe.domain.member.domain.vo.*;
import team.backend.goWithMe.domain.trip.error.exception.TimeTableNameInvalidException;
import team.backend.goWithMe.domain.trip.error.exception.TimeTablePeriodInvalidException;
import team.backend.goWithMe.domain.trip.domain.vo.TimeTableContent;
import team.backend.goWithMe.domain.trip.domain.vo.TimeTableName;
import team.backend.goWithMe.domain.trip.domain.vo.TimeTablePeriod;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TimeTableTest {

    private Member member;
    private TimeTable timeTable;
    final private String OLD_NAME = "전체 일정";
    final private String OLD_CONTENT = "일정은 이러이러 해~";
    final private LocalDateTime OLD_PERIOD_START = LocalDateTime.of(2022, 1, 1, 0, 0);
    final private LocalDateTime OLD_PERIOD_END = LocalDateTime.of(2022, 1, 10, 0, 0);

    @BeforeEach
    void init() {
        this.member = Member.builder()
                .name(UserName.from("정의재"))
                .birth(LocalDate.of(2021, 1, 1))
                .nickname(UserNickName.from("JJ"))
                .email(UserEmail.from("abc@naver.com"))
                .password(UserPassword.from("1234"))
                .roleType(RoleType.USER)
                .profileImage(UserProfileImage.from("sampleURL"))
                .build();
        TimeTableName timeTableName = TimeTableName.from(OLD_NAME);
        TimeTableContent timeTableContent = TimeTableContent.from(OLD_CONTENT);
        TimeTablePeriod timeTablePeriod = TimeTablePeriod.between(OLD_PERIOD_START, OLD_PERIOD_END);
        this.timeTable = TimeTable.createTimeTable(timeTableName, timeTableContent,
                timeTablePeriod, member);
    }

    @Test
    @DisplayName("VO 객체 증명 테스트1 - 값이 같으면 같은 객체")
    public void voEqualsTest1() throws Exception {
        // given
        TimeTablePeriod period = TimeTablePeriod.between(OLD_PERIOD_START, OLD_PERIOD_END);
        TimeTableContent content = TimeTableContent.from(OLD_CONTENT);

        // when
        TimeTableName a = TimeTableName.from("a");
        TimeTableName b = TimeTableName.from("a");

        TimeTable aTable = TimeTable.createTimeTable(a, content, period, member);
        TimeTable bTable = TimeTable.createTimeTable(b, content, period, member);
        // then
        assertNotEquals(aTable, bTable);
        assertEquals(a, b);
        assertEquals(aTable.getTableName(), bTable.getTableName());
        assertEquals(aTable.getContent(), bTable.getContent());
        assertEquals(aTable.getTotalPeriod(), bTable.getTotalPeriod());
    }

    @Test
    @DisplayName("VO 객체 증명 테스트2 - 값이 다르면 다른 객체")
    public void voEqualsTest2() throws Exception {
        // given
        TimeTablePeriod period = TimeTablePeriod.between(OLD_PERIOD_START, OLD_PERIOD_END);
        TimeTableContent content = TimeTableContent.from(OLD_CONTENT);

        // when
        TimeTableName a = TimeTableName.from("a");
        TimeTableName b = TimeTableName.from("b");

        TimeTable aTable = TimeTable.createTimeTable(a, content, period, member);
        TimeTable bTable = TimeTable.createTimeTable(b, content, period, member);
        // then
        assertNotEquals(aTable, bTable);
        assertNotEquals(a, b);
        assertNotEquals(aTable.getTableName(), bTable.getTableName());
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
        TimeTable.createTimeTable(null, null, null, member));
        assertThrows(NullPointerException.class, () ->
        TimeTable.createTimeTable(timeTableName, null, null, member));
        assertThrows(NullPointerException.class, () ->
        TimeTable.createTimeTable(null, timeTableContent, null, member));
        assertThrows(NullPointerException.class, () ->
        TimeTable.createTimeTable(null, null, timeTablePeriod, member));
        assertThrows(NullPointerException.class, () ->
        TimeTable.createTimeTable(timeTableName, timeTableContent, null, member));
        assertThrows(NullPointerException.class, () ->
        TimeTable.createTimeTable(null, timeTableContent, timeTablePeriod, member));
        assertThrows(NullPointerException.class, () -> TimeTable.createTimeTable(timeTableName, null, timeTablePeriod, member));
        assertDoesNotThrow(() -> TimeTable.createTimeTable(timeTableName, timeTableContent, timeTablePeriod, member));
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
        TimeTableName oldTableName = timeTable.getTableName();

        // when
        TimeTable editedTimeTable = TimeTable.createTimeTable(
                TimeTableName.from("일정2"),
                timeTable.getContent(),
                timeTable.getTotalPeriod(),
                timeTable.getMember()
        );
        timeTable.changeTimeTable(editedTimeTable);

        // then
        TimeTableName newTableName = timeTable.getTableName();
        assertNotEquals(oldTableName, newTableName);
        assertEquals(OLD_NAME, oldTableName.timeTableName());
        assertEquals("일정2", newTableName.timeTableName());
    }

    @Test
    @DisplayName("시간표 명 null 로 변경 테스트")
    public void changeTimeTableNameToNullTest() throws Exception {
        // given
        // when, then
        assertThrows(TimeTableNameInvalidException.class, () ->
                TimeTable.createTimeTable(
                        TimeTableName.from(null),
                        timeTable.getContent(),
                        timeTable.getTotalPeriod(),
                        timeTable.getMember()
                ));
    }

    @Test
    @DisplayName("시간표 명 \"\" 로 변경 테스트")
    public void changeTimeTableNameToBlankTest() throws Exception {
        // given
        // when, then
        assertThrows(TimeTableNameInvalidException.class, () -> timeTable.changeTimeTable(
                TimeTable.createTimeTable(
                        TimeTableName.from(""),
                        timeTable.getContent(),
                        timeTable.getTotalPeriod(),
                        timeTable.getMember()
                )
        ));
    }

    @Test
    @DisplayName("시간표 내용 변경 테스트")
    public void changeTableContentTest() throws Exception {
        // given
        final String NEW_CONTENT = "새 일정은 이러이러해~";
        TimeTableContent oldContent = timeTable.getContent();

        // when
        TimeTable editedTimeTable = TimeTable.createTimeTable(
                timeTable.getTableName(),
                TimeTableContent.from(NEW_CONTENT),
                timeTable.getTotalPeriod(),
                timeTable.getMember()
        );
        timeTable.changeTimeTable(editedTimeTable);

        // then
        TimeTableContent newContent = timeTable.getContent();

        assertNotEquals(oldContent, newContent);
        assertEquals(OLD_CONTENT, oldContent.tableContent());
        assertEquals(NEW_CONTENT, newContent.tableContent());
    }

    @Test
    @DisplayName("시간표 내용 초기화 테스트")
    public void resetTableContentTest() throws Exception {
        // given
        TimeTableContent oldContent = timeTable.getContent();
        // when
        timeTable.resetTableContent();
        // then
        TimeTableContent resetContent = timeTable.getContent();

        assertNotEquals(oldContent, resetContent);
        assertEquals(OLD_CONTENT, oldContent.tableContent());
        assertEquals("", resetContent.tableContent());
    }

    @Test
    @DisplayName("전체 일정 시작 지점 변경 테스트")
    public void changeTotalPeriodStartTest() throws Exception {
        // given
        LocalDateTime oldTotalStart = timeTable.getTotalPeriod().totalPeriodStart();
        LocalDateTime newTimeStart = LocalDateTime.of(2022, 1, 2, 0, 0);
        // when
        TimeTablePeriod newPeriod = TimeTablePeriod.between(newTimeStart, OLD_PERIOD_END);
        TimeTable editedTimeTable = TimeTable.createTimeTable(
                timeTable.getTableName(),
                timeTable.getContent(),
                newPeriod,
                timeTable.getMember()
        );
        timeTable.changeTimeTable(editedTimeTable);

        // then
        LocalDateTime newTotalStart = timeTable.getTotalPeriod().totalPeriodStart();
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

        // when, then
        assertThrows(TimeTablePeriodInvalidException.class, () -> timeTable.changeTimeTable(
                TimeTable.createTimeTable(
                    timeTable.getTableName(),
                    timeTable.getContent(),
                    TimeTablePeriod.between(dateLaterThanTotalEnd1, OLD_PERIOD_START),
                    timeTable.getMember()
                )
        ));
    }

    @Test
    @DisplayName("일정 끝 지점 변경 테스트")
    public void changeTotalPeriodEndTest() throws Exception {
        // given
        LocalDateTime oldTotalEnd = timeTable.getTotalPeriod().totalPeriodEnd();
        LocalDateTime newTimeEnd = LocalDateTime.of(2022, 1, 11, 0, 0);

        // when
        TimeTablePeriod newPeriod = TimeTablePeriod.between(OLD_PERIOD_START, newTimeEnd);
        TimeTable editedTimeTable = TimeTable.createTimeTable(
                timeTable.getTableName(),
                timeTable.getContent(),
                newPeriod,
                timeTable.getMember()
        );
        timeTable.changeTimeTable(editedTimeTable);

        // then
        LocalDateTime newTotalEnd = timeTable.getTotalPeriod().totalPeriodEnd();

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
        assertThrows(TimeTablePeriodInvalidException.class, () -> timeTable.changeTimeTable(
                TimeTable.createTimeTable(
                    timeTable.getTableName(),
                    timeTable.getContent(),
                    TimeTablePeriod.between(OLD_PERIOD_END, dateEarlierThanTotalStart1),
                    timeTable.getMember()
                )
            )
        );
        assertThrows(TimeTablePeriodInvalidException.class, () -> timeTable.changeTimeTable(
                TimeTable.createTimeTable(
                    timeTable.getTableName(),
                    timeTable.getContent(),
                    TimeTablePeriod.between(OLD_PERIOD_END, dateEarlierThanTotalStart2),
                    timeTable.getMember()
                )
            )
        );
        assertThrows(TimeTablePeriodInvalidException.class, () -> timeTable.changeTimeTable(
                TimeTable.createTimeTable(
                    timeTable.getTableName(),
                    timeTable.getContent(),
                    TimeTablePeriod.between(OLD_PERIOD_END, dateEarlierThanTotalStart3),
                    timeTable.getMember()
                )
            )
        );
    }

    @Test
    @DisplayName("일정 전체 변경 테스트")
    public void changeTotalPeriodTest() throws Exception {
        // given
        LocalDateTime newTimeStart = LocalDateTime.of(2022, 1, 9, 0, 0);
        LocalDateTime newTimeEnd = LocalDateTime.of(2022, 1, 31, 0, 0);

        // when
        TimeTablePeriod newPeriod = TimeTablePeriod.between(newTimeStart, newTimeEnd);
        TimeTable editedTimeTable = TimeTable.createTimeTable(
                timeTable.getTableName(),
                timeTable.getContent(),
                newPeriod,
                timeTable.getMember()
        );
        timeTable.changeTimeTable(editedTimeTable);

        // then
        assertEquals(newTimeStart, timeTable.getTotalPeriod().totalPeriodStart());
        assertEquals(newTimeEnd, timeTable.getTotalPeriod().totalPeriodEnd());
        assertEquals(
                Duration.between(newTimeStart, newTimeEnd),
                Duration.between( timeTable.getTotalPeriod().totalPeriodStart(), timeTable.getTotalPeriod().totalPeriodEnd())
        );
    }

    @Test
    @DisplayName("일정 시작지점보다 빠른 일정 끝지점, 오류 테스트")
    public void changeTotalPeriodToWrongTest1() throws Exception {
        // given
        LocalDateTime timeStart = LocalDateTime.of(2022, 1, 9, 0, 0);
        LocalDateTime timeEndEarlierThanTimeStart = LocalDateTime.of(2022, 1, 1, 0, 0);

        // when, than
        assertThrows(TimeTablePeriodInvalidException.class, () -> timeTable.changeTimeTable(
                TimeTable.createTimeTable(
                    timeTable.getTableName(),
                    timeTable.getContent(),
                    TimeTablePeriod.between(timeStart, timeEndEarlierThanTimeStart),
                    timeTable.getMember()
                )
        ));
    }

    @Test
    @DisplayName("일정 시작지점과 동일한 끝 지점, 오류 테스트")
    public void changeTotalPeriodToWrongTest2() throws Exception {
        // given
        LocalDateTime timeStart = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime timeEndEqualToTimeStart = LocalDateTime.of(2022, 1, 1, 0, 0);

        // when, than
        assertThrows(TimeTablePeriodInvalidException.class, () -> timeTable.changeTimeTable(
                TimeTable.createTimeTable(
                        timeTable.getTableName(),
                        timeTable.getContent(),
                        TimeTablePeriod.between(timeStart, timeEndEqualToTimeStart),
                        timeTable.getMember()
                )
        ));
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
        TimeTable editedTimeTable = TimeTable.createTimeTable(
                TimeTableName.from(newName),
                TimeTableContent.from(newContent),
                TimeTablePeriod.between(newPeriodStart, newPeriodEnd),
                timeTable.getMember()
        );
        timeTable.changeTimeTable(editedTimeTable);

        // then
        assertEquals(timeTable.getId(), timeTable.getId());
        assertNotEquals(OLD_NAME, timeTable.getTableName().timeTableName());
        assertNotEquals(OLD_CONTENT, timeTable.getContent().tableContent());
        assertNotEquals(OLD_PERIOD_START, timeTable.getTotalPeriod().totalPeriodStart());
        assertNotEquals(OLD_PERIOD_END, timeTable.getTotalPeriod().totalPeriodEnd());
        assertEquals(newName, timeTable.getTableName().timeTableName());
        assertEquals(newContent, timeTable.getContent().tableContent());
        assertEquals(newPeriodStart, timeTable.getTotalPeriod().totalPeriodStart());
        assertEquals(newPeriodEnd, timeTable.getTotalPeriod().totalPeriodEnd());
    }


}