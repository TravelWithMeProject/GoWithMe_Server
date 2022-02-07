package team.backend.goWithMe.domain.trip.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import team.backend.goWithMe.domain.member.domain.persist.Member;
import team.backend.goWithMe.domain.member.domain.persist.RoleType;
import team.backend.goWithMe.domain.member.domain.vo.*;
import team.backend.goWithMe.domain.trip.domain.persist.TimeTable;
import team.backend.goWithMe.domain.trip.domain.vo.TimeTableContent;
import team.backend.goWithMe.domain.trip.domain.vo.TimeTableName;
import team.backend.goWithMe.domain.trip.domain.vo.TimeTablePeriod;
import team.backend.goWithMe.domain.trip.dto.request.TimeTableCreateDTO;
import team.backend.goWithMe.domain.trip.dto.response.TimeTableDTO;
import team.backend.goWithMe.domain.trip.dto.response.TimeTableListDTO;
import team.backend.goWithMe.domain.trip.error.exception.NoSuchMemberException;
import team.backend.goWithMe.domain.trip.error.exception.NoSuchTimeTableException;
import team.backend.goWithMe.domain.trip.error.exception.WrongTimeTableOwnerIdException;
import team.backend.goWithMe.domain.trip.repository.MemberRepository;
import team.backend.goWithMe.domain.trip.repository.ScheduleRepository;
import team.backend.goWithMe.domain.trip.repository.TimeTableRepository;
import team.backend.goWithMe.global.error.exception.ErrorCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class TimeTableServiceTest {
    @InjectMocks
    private TimeTableService timeTableService;

    @Mock
    private TimeTableRepository timeTableRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Test
    @DisplayName("시간표 저장 테스트 - 성공 케이스")
    public void timeTableSaveTest() throws Exception {
        // given
        Member member = member();
        Long fakeUserId = 11L;
        ReflectionTestUtils.setField(member, "id", fakeUserId);
        given(memberRepository.findById(fakeUserId)).willReturn(Optional.of(member));

        TimeTableCreateDTO timeTableCreateDTO = timeTableCreateDTO(member.getId(), "시간표 이름");
        TimeTable timeTable = createTimeTable(timeTableCreateDTO, member);
        Long fakeTimeTableId = 3L;
        ReflectionTestUtils.setField(timeTable, "id", fakeTimeTableId);

        given(timeTableRepository.save(any())).willReturn(timeTable);
        given(timeTableRepository.findById(fakeTimeTableId)).willReturn(Optional.of(timeTable));

        // when
        Long savedTimeTableId = timeTableService.createTimeTable(timeTableCreateDTO);

        // then
        TimeTable findTimeTable = timeTableRepository.findById(savedTimeTableId).get();

        assertThat(findTimeTable).isEqualTo(findTimeTable);
        assertThat(findTimeTable.getId()).isEqualTo(timeTable.getId());
        assertThat(findTimeTable.getTableName()).isEqualTo(timeTable.getTableName());
    }

    @Test
    @DisplayName("시간표 아이디로 찾기 테스트 - 성공 케이스")
    public void timeTableFindByIdTest() throws Exception {
        // given
        Member member = member();
        Long fakeUserId = 11L;
        ReflectionTestUtils.setField(member, "id", fakeUserId);
        given(memberRepository.findById(fakeUserId)).willReturn(Optional.of(member));

        TimeTableCreateDTO timeTableCreateDTO = timeTableCreateDTO(member.getId(), "시간표 이름");
        TimeTable timeTable = createTimeTable(timeTableCreateDTO, member);
        Long fakeTimeTableId = 3L;
        ReflectionTestUtils.setField(timeTable, "id", fakeTimeTableId);

        given(timeTableRepository.save(any())).willReturn(timeTable);
        given(timeTableRepository.findById(fakeTimeTableId)).willReturn(Optional.of(timeTable));

        Long savedTimeTableId = timeTableService.createTimeTable(timeTableCreateDTO);

        // when
        TimeTableDTO timeTableDTO = timeTableService.findTimeTable(member.getId(), savedTimeTableId);

        // then
        assertThat(timeTableDTO.getTimeTableId()).isEqualTo(savedTimeTableId);
        assertThat(timeTableDTO.getTableName()).isEqualTo(timeTable.getTableName().timeTableName());
        assertThat(timeTableDTO.getContent()).isEqualTo(timeTable.getContent().tableContent());
    }

    @Test
    @DisplayName("시간표 아이디로 찾기 테스트 실패(예외) 케이스1 - 사용자 id(O), 시간표 id(X)")
    public void timeTableFindByIdFailTest() throws Exception {
        // given
        Member member = member();
        Long fakeUserId = 11L;
        ReflectionTestUtils.setField(member, "id", fakeUserId);
        given(memberRepository.findById(fakeUserId)).willReturn(Optional.of(member));

        TimeTableCreateDTO timeTableCreateDTO = timeTableCreateDTO(member.getId(), "시간표 이름");
        TimeTable timeTable = createTimeTable(timeTableCreateDTO, member);
        Long fakeTimeTableId = 3L;
        ReflectionTestUtils.setField(timeTable, "id", fakeTimeTableId);
        Long wrongTimeTableId = 0L;

        given(timeTableRepository.save(any())).willReturn(timeTable);
        given(timeTableRepository.findById(fakeTimeTableId)).willReturn(Optional.of(timeTable));
        given(timeTableRepository.findById(wrongTimeTableId))
                .willThrow(new NoSuchTimeTableException(ErrorCode.NO_SUCH_TIMETABLE));
        Long savedTimeTableId = timeTableService.createTimeTable(timeTableCreateDTO);

        // when, then
        TimeTableDTO foundTimeTableDTO = timeTableService.findTimeTable(member.getId(), savedTimeTableId);

        assertThat(foundTimeTableDTO.getTableName()).isEqualTo(timeTable.getTableName().timeTableName());
        assertThrows(NoSuchTimeTableException.class, () -> timeTableService.findTimeTable(member.getId(), wrongTimeTableId));
    }

    @Test
    @DisplayName("시간표 아이디로 찾기 테스트 실패(예외) 케이스2 - 사용자 id(X), 시간표 id(O)")
    public void timeTableFindByIdFailTest2() throws Exception {
        // given
        Member member = member();
        Long fakeUserId = 11L;
        ReflectionTestUtils.setField(member, "id", fakeUserId);
        given(memberRepository.findById(fakeUserId)).willReturn(Optional.of(member));

        TimeTableCreateDTO timeTableCreateDTO = timeTableCreateDTO(member.getId(), "시간표 이름");
        TimeTable timeTable = createTimeTable(timeTableCreateDTO, member);
        Long fakeTimeTableId = 3L;
        ReflectionTestUtils.setField(timeTable, "id", fakeTimeTableId);

        given(timeTableRepository.save(any())).willReturn(timeTable);
        given(timeTableRepository.findById(fakeTimeTableId)).willReturn(Optional.of(timeTable));
        Long savedTimeTableId = timeTableService.createTimeTable(timeTableCreateDTO);

        // when, then
        Long wrongUserId = 9L;
        assertThrows(WrongTimeTableOwnerIdException.class, () -> timeTableService.findTimeTable(wrongUserId, savedTimeTableId));
    }

    @Test
    @DisplayName("시간표 아이디로 찾기 테스트 실패(예외) 케이스3 - 사용자 id(X), 시간표 id(X)")
    public void timeTableFindByIdFailTest3() throws Exception {
        // given
        Member member = member();
        Long fakeUserId = 11L;
        ReflectionTestUtils.setField(member, "id", fakeUserId);
        given(memberRepository.findById(fakeUserId)).willReturn(Optional.of(member));

        TimeTableCreateDTO timeTableCreateDTO = timeTableCreateDTO(member.getId(), "시간표 이름");
        TimeTable timeTable = createTimeTable(timeTableCreateDTO, member);
        Long fakeTimeTableId = 3L;
        ReflectionTestUtils.setField(timeTable, "id", fakeTimeTableId);

        given(timeTableRepository.save(any())).willReturn(timeTable);
        timeTableService.createTimeTable(timeTableCreateDTO);

        // when, then
        Long wrongUserId = 9L;
        Long wrongTimeTableId = 12L;
        assertThrows(NoSuchMemberException.class, () -> timeTableService.findTimeTable(wrongUserId, wrongTimeTableId));
    }

    @Test
    @DisplayName("사용자 id로 사용자가 생성한 테이블 가져오기-1(성공 케이스A: 생성한 테이블이 1개인 경우)")
    public void findTImeTablesByMemberIdTest() throws Exception {
        // given
        Member member = member();
        Long fakeUserId = 11L;
        ReflectionTestUtils.setField(member, "id", fakeUserId);
        given(memberRepository.findById(fakeUserId)).willReturn(Optional.of(member));

        TimeTableCreateDTO timeTableCreateDTO = timeTableCreateDTO(member.getId(), "시간표 이름");
        TimeTable timeTable = createTimeTable(timeTableCreateDTO, member);
        Long fakeTimeTableId = 3L;
        ReflectionTestUtils.setField(timeTable, "id", fakeTimeTableId);

        given(timeTableRepository.save(any())).willReturn(timeTable);
        timeTableService.createTimeTable(timeTableCreateDTO);

        given(timeTableRepository.findByUserId(fakeUserId)).willReturn(Collections.singletonList(timeTable));

        // when
        TimeTableListDTO timeTableListDTO = timeTableService.findTimeTablesByMemberId(member.getId());

        // then
        TimeTableDTO timeTableResponseDTO = timeTableListDTO.getTimeTableList().get(0);
        assertThat(timeTableResponseDTO.getTimeTableId()).isEqualTo(timeTable.getId());
        assertThat(timeTableResponseDTO.getTableName()).isEqualTo(timeTable.getTableName().timeTableName());
    }

    @Test
    @DisplayName("사용자 id로 사용자가 생성한 테이블 가져오기-2(성공 케이스B: 생성한 테이블이 2개인(여러개) 경우)")
    public void findTImeTablesByMemberIdTest2() throws Exception {
        // given
        Member member = member();
        Long fakeUserId = 11L;
        ReflectionTestUtils.setField(member, "id", fakeUserId);
        given(memberRepository.findById(fakeUserId)).willReturn(Optional.of(member));

        // 시간표 2개 생성
        TimeTableCreateDTO timeTableCreateDTO_1 = timeTableCreateDTO(member.getId(), "시간표 이름1");
        TimeTable timeTable1 = createTimeTable(timeTableCreateDTO_1, member);
        Long fakeTimeTableId1 = 3L;
        ReflectionTestUtils.setField(timeTable1, "id", fakeTimeTableId1);

        TimeTableCreateDTO timeTableCreateDTO_2 = timeTableCreateDTO(member.getId(), "시간표 이름2");
        TimeTable timeTable2 = createTimeTable(timeTableCreateDTO_2, member);
        Long fakeTimeTableId2 = 5L;
        ReflectionTestUtils.setField(timeTable2, "id", fakeTimeTableId2);

        given(timeTableRepository.save(any())).willReturn(timeTable1);
        timeTableService.createTimeTable(timeTableCreateDTO_1);
        timeTableService.createTimeTable(timeTableCreateDTO_1);

        given(timeTableRepository.findByUserId(fakeUserId)).willReturn(Arrays.asList(timeTable1, timeTable2));

        // when
        TimeTableListDTO timeTableListDTO = timeTableService.findTimeTablesByMemberId(member.getId());

        // then
        TimeTableDTO timeTableResponseDTO_1 = timeTableListDTO.getTimeTableList().get(0);
        TimeTableDTO timeTableResponseDTO_2 = timeTableListDTO.getTimeTableList().get(1);

        assertThat(timeTableListDTO.getTimeTableList().size()).isEqualTo(2);
        assertThat(timeTableResponseDTO_1.getTimeTableId()).isEqualTo(timeTable1.getId());
        assertThat(timeTableResponseDTO_1.getTableName()).isEqualTo(timeTable1.getTableName().timeTableName());
        assertThat(timeTableResponseDTO_2.getTimeTableId()).isEqualTo(timeTable2.getId());
        assertThat(timeTableResponseDTO_2.getTableName()).isEqualTo(timeTable2.getTableName().timeTableName());
    }

    @Test
    @DisplayName("사용자 id로 사용자가 생성한 테이블 가져오기-3(성공 케이스C: 생성한 테이블이 0개인 경우)")
    public void findTImeTablesByMemberIdTest3() throws Exception {
        // given
        Member member = member();
        Long fakeUserId = 11L;
        ReflectionTestUtils.setField(member, "id", fakeUserId);
        given(memberRepository.findById(fakeUserId)).willReturn(Optional.of(member));

        given(timeTableRepository.findByUserId(fakeUserId)).willReturn(Collections.emptyList());

        // when
        TimeTableListDTO timeTableListDTO = timeTableService.findTimeTablesByMemberId(member.getId());

        // then
        assertThat(timeTableListDTO.getTimeTableList().size()).isEqualTo(0);
        assertThat(timeTableListDTO.getTimeTableList().isEmpty()).isTrue();
    }

    @Test
    @DisplayName("사용자 id로 사용자가 생성한 테이블 가져오기-4(실패 케이스: 존재하지 않는 사용자 id)")
    public void findTimeTablesByMemberIdTest4() throws Exception {
        // given
        Member member = member();
        Long fakeUserId = 11L;
        ReflectionTestUtils.setField(member, "id", fakeUserId);
        Long nonExistentUserId = 0L;
        given(memberRepository.findById(nonExistentUserId)).willThrow(new NoSuchMemberException(ErrorCode.NO_SUCH_MEMBER_IN_TIMETABLE));

        // when, then
        assertThrows(NoSuchMemberException.class, () -> timeTableService.findTimeTablesByMemberId(nonExistentUserId));
    }

    @Test
    @DisplayName("시간표 삭제 테스트-1(성공 케이스)")
    public void deleteTimeTableTest1() throws Exception {
        // given
        Member member = member();
        Long fakeUserId = 11L;
        ReflectionTestUtils.setField(member, "id", fakeUserId);
        given(memberRepository.findById(fakeUserId)).willReturn(Optional.of(member));

        TimeTableCreateDTO timeTableCreateDTO = timeTableCreateDTO(member.getId(), "시간표 이름");
        TimeTable timeTable = createTimeTable(timeTableCreateDTO, member);
        Long fakeTimeTableId = 3L;
        ReflectionTestUtils.setField(timeTable, "id", fakeTimeTableId);

        given(timeTableRepository.save(any())).willReturn(timeTable);
        given(timeTableRepository.findById(fakeTimeTableId)).willReturn(Optional.of(timeTable));
        given(scheduleRepository.findByTimeTableId(timeTable.getId())).willReturn(Collections.emptyList());

        Long savedTimeTableId = timeTableService.createTimeTable(timeTableCreateDTO);
        // when, then
        assertDoesNotThrow(() -> timeTableService.deleteTimeTable(member.getId(), savedTimeTableId));
    }

    @Test
    @DisplayName("시간표 삭제 테스트-2(실패 케이스A: 사용자 id(X), 시간표 id(O))")
    public void deleteTimeTableTest2() throws Exception {
        // given
        Member member = member();
        Long fakeUserId = 11L;
        ReflectionTestUtils.setField(member, "id", fakeUserId);
        given(memberRepository.findById(fakeUserId)).willReturn(Optional.of(member));

        TimeTableCreateDTO timeTableCreateDTO = timeTableCreateDTO(member.getId(), "시간표 이름");
        TimeTable timeTable = createTimeTable(timeTableCreateDTO, member);
        Long fakeTimeTableId = 3L;
        ReflectionTestUtils.setField(timeTable, "id", fakeTimeTableId);

        given(timeTableRepository.save(any())).willReturn(timeTable);
        given(timeTableRepository.findById(fakeTimeTableId)).willReturn(Optional.of(timeTable));

        Long savedTimeTableId = timeTableService.createTimeTable(timeTableCreateDTO);
        // when, then
        Long nonExistentUserId = 0L;
        assertThrows(WrongTimeTableOwnerIdException.class, () -> timeTableService.deleteTimeTable(nonExistentUserId, savedTimeTableId));
    }

    @Test
    @DisplayName("시간표 삭제 테스트-3(실패 케이스B: 사용자 id(O), 시간표 id(X))")
    public void deleteTimeTableTest3() throws Exception {
        // given
        Member member = member();
        Long fakeUserId = 11L;
        ReflectionTestUtils.setField(member, "id", fakeUserId);
        given(memberRepository.findById(fakeUserId)).willReturn(Optional.of(member));

        TimeTableCreateDTO timeTableCreateDTO = timeTableCreateDTO(member.getId(), "시간표 이름");
        TimeTable timeTable = createTimeTable(timeTableCreateDTO, member);
        Long fakeTimeTableId = 3L;
        ReflectionTestUtils.setField(timeTable, "id", fakeTimeTableId);

        given(timeTableRepository.save(any())).willReturn(timeTable);

        timeTableService.createTimeTable(timeTableCreateDTO);
        // when, then
        Long nonExistentUserId = 0L;
        Long nonExistentTimeTableId = 0L;
        assertThrows(NoSuchTimeTableException.class, () -> timeTableService.deleteTimeTable(nonExistentUserId, nonExistentTimeTableId));
    }

    @Test
    @DisplayName("시간표 삭제 테스트-4(실패 케이스C: 사용자 id(X), 시간표 id(X))")
    public void deleteTimeTableTest4() throws Exception {
        // given
        Member member = member();
        Long fakeUserId = 11L;
        ReflectionTestUtils.setField(member, "id", fakeUserId);
        given(memberRepository.findById(fakeUserId)).willReturn(Optional.of(member));

        TimeTableCreateDTO timeTableCreateDTO = timeTableCreateDTO(member.getId(), "시간표 이름");
        TimeTable timeTable = createTimeTable(timeTableCreateDTO, member);
        Long fakeTimeTableId = 3L;
        ReflectionTestUtils.setField(timeTable, "id", fakeTimeTableId);

        given(timeTableRepository.save(any())).willReturn(timeTable);

        timeTableService.createTimeTable(timeTableCreateDTO);
        // when, then
        Long nonExistentTimeTableId = 0L;
        assertThrows(NoSuchTimeTableException.class, () -> timeTableService.deleteTimeTable(member.getId(), nonExistentTimeTableId));
    }

    @Test
    @DisplayName("시간표 업데이트 테스트-1(성공 케이스)")
    public void updateTimeTableTest1() throws Exception {
        // given
        Member member = member();
        Long fakeUserId = 11L;
        ReflectionTestUtils.setField(member, "id", fakeUserId);
        given(memberRepository.findById(fakeUserId)).willReturn(Optional.of(member));

        TimeTableCreateDTO timeTableCreateDTO = timeTableCreateDTO(member.getId(), "시간표 이름");
        TimeTable timeTable = createTimeTable(timeTableCreateDTO, member);
        Long fakeTimeTableId = 3L;
        ReflectionTestUtils.setField(timeTable, "id", fakeTimeTableId);

        given(timeTableRepository.save(any())).willReturn(timeTable);
        given(timeTableRepository.findById(fakeTimeTableId)).willReturn(Optional.of(timeTable));

        timeTableService.createTimeTable(timeTableCreateDTO);

        // when
        final String NEW_TIMETABLE_NAME = "New 시간표 이름";
        TimeTableCreateDTO timeTableCreateDTOForUpdate = timeTableCreateDTO(member.getId(), NEW_TIMETABLE_NAME);
        timeTableService.updateTimeTable(timeTable.getId(), timeTableCreateDTOForUpdate);

        // then
        assertThat(timeTable.getTableName().timeTableName()).isEqualTo(NEW_TIMETABLE_NAME);
        assertThat(timeTable.getContent().tableContent()).isEqualTo(timeTableCreateDTO.getTimeTableContent());
    }

    @Test
    @DisplayName("시간표 업데이트 테스트-3(실패 케이스: 가장 빠른 세부 일정 시작지점보다 늦은 시간으로의 시간 조정)")
    public void updateTimeTableTest2() throws Exception {
        // given
        Member member = member();
        Long fakeUserId = 11L;
        ReflectionTestUtils.setField(member, "id", fakeUserId);
        given(memberRepository.findById(fakeUserId)).willReturn(Optional.of(member));

        TimeTableCreateDTO timeTableCreateDTO = timeTableCreateDTO(member.getId(), "시간표 이름");
        TimeTable timeTable = createTimeTable(timeTableCreateDTO, member);
        Long fakeTimeTableId = 3L;
        ReflectionTestUtils.setField(timeTable, "id", fakeTimeTableId);

        given(timeTableRepository.save(any())).willReturn(timeTable);

        timeTableService.createTimeTable(timeTableCreateDTO);

        // when
        final String NEW_TIMETABLE_NAME = "New 시간표 이름";
        Long nonExistentTimeTableId = 0L;
        TimeTableCreateDTO timeTableCreateDTOForUpdate = timeTableCreateDTO(member.getId(), NEW_TIMETABLE_NAME);

        // then
        assertThrows(NoSuchTimeTableException.class, () ->
                timeTableService.updateTimeTable(nonExistentTimeTableId, timeTableCreateDTOForUpdate));
    }

    /**
     * 시간표 엔티티 생성용 함수 for Test
     */
    private TimeTable createTimeTable(TimeTableCreateDTO timeTableCreateDTO, Member member) {
        return TimeTable.createTimeTable(
                TimeTableName.from(timeTableCreateDTO.getTimeTableName()),
                TimeTableContent.from(timeTableCreateDTO.getTimeTableContent()),
                TimeTablePeriod.between(timeTableCreateDTO.getTotalPeriodStart(), timeTableCreateDTO.getTotalPeriodEnd()),
                member);
    }


    /**
     * 시간표 requestDTO 객체 생성용 함수 for Test
     * -> parameter로 굳이 timeTableName을 받은 건 여러 개의 TimeTable 객체를 만들 때 구분 짓기 위해서임
     */
    private TimeTableCreateDTO timeTableCreateDTO(Long userId, String timeTableName) {
        return new TimeTableCreateDTO(
                userId,
                timeTableName,
                "시간표 내용 is ...",
                LocalDateTime.of(2022, 1, 1, 0, 0),
                LocalDateTime.of(2022, 1, 5, 21, 0)
        );
    }

    /**
     * Member 엔티티 생성용 함수 for Test
     */
    private Member member() {
        return Member.builder().name(UserName.from("정의재"))
                .nickname(UserNickName.from("JJ"))
                .password(UserPassword.from("1234"))
                .email(UserEmail.from("abc123@naver.com"))
                .roleType(RoleType.USER)
                .profileImage(UserProfileImage.from("profile.url"))
                .birth(LocalDate.of(2020, 1, 1))
                .build();
    }


}