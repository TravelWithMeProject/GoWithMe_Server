package team.backend.goWithMe.domain.trip.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import team.backend.goWithMe.domain.member.domain.persist.Member;
import team.backend.goWithMe.domain.member.domain.persist.MemberRepository;
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
    @DisplayName("????????? ?????? ????????? - ?????? ?????????")
    public void timeTableSaveTest() throws Exception {
        // given
        Member member = member();
        Long fakeUserId = 11L;
        ReflectionTestUtils.setField(member, "id", fakeUserId);
        given(memberRepository.findById(fakeUserId)).willReturn(Optional.of(member));

        TimeTableCreateDTO timeTableCreateDTO = timeTableCreateDTO(member.getId(), "????????? ??????");
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
    @DisplayName("????????? ???????????? ?????? ????????? - ?????? ?????????")
    public void timeTableFindByIdTest() throws Exception {
        // given
        Member member = member();
        Long fakeUserId = 11L;
        ReflectionTestUtils.setField(member, "id", fakeUserId);
        given(memberRepository.findById(fakeUserId)).willReturn(Optional.of(member));

        TimeTableCreateDTO timeTableCreateDTO = timeTableCreateDTO(member.getId(), "????????? ??????");
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
    @DisplayName("????????? ???????????? ?????? ????????? ??????(??????) ?????????1 - ????????? id(O), ????????? id(X)")
    public void timeTableFindByIdFailTest() throws Exception {
        // given
        Member member = member();
        Long fakeUserId = 11L;
        ReflectionTestUtils.setField(member, "id", fakeUserId);
        given(memberRepository.findById(fakeUserId)).willReturn(Optional.of(member));

        TimeTableCreateDTO timeTableCreateDTO = timeTableCreateDTO(member.getId(), "????????? ??????");
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
    @DisplayName("????????? ???????????? ?????? ????????? ??????(??????) ?????????2 - ????????? id(X), ????????? id(O)")
    public void timeTableFindByIdFailTest2() throws Exception {
        // given
        Member member = member();
        Long fakeUserId = 11L;
        ReflectionTestUtils.setField(member, "id", fakeUserId);
        given(memberRepository.findById(fakeUserId)).willReturn(Optional.of(member));

        TimeTableCreateDTO timeTableCreateDTO = timeTableCreateDTO(member.getId(), "????????? ??????");
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
    @DisplayName("????????? ???????????? ?????? ????????? ??????(??????) ?????????3 - ????????? id(X), ????????? id(X)")
    public void timeTableFindByIdFailTest3() throws Exception {
        // given
        Member member = member();
        Long fakeUserId = 11L;
        ReflectionTestUtils.setField(member, "id", fakeUserId);
        given(memberRepository.findById(fakeUserId)).willReturn(Optional.of(member));

        TimeTableCreateDTO timeTableCreateDTO = timeTableCreateDTO(member.getId(), "????????? ??????");
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
    @DisplayName("????????? id??? ???????????? ????????? ????????? ????????????-1(?????? ?????????A: ????????? ???????????? 1?????? ??????)")
    public void findTImeTablesByMemberIdTest() throws Exception {
        // given
        Member member = member();
        Long fakeUserId = 11L;
        ReflectionTestUtils.setField(member, "id", fakeUserId);
        given(memberRepository.findById(fakeUserId)).willReturn(Optional.of(member));

        TimeTableCreateDTO timeTableCreateDTO = timeTableCreateDTO(member.getId(), "????????? ??????");
        TimeTable timeTable = createTimeTable(timeTableCreateDTO, member);
        Long fakeTimeTableId = 3L;
        ReflectionTestUtils.setField(timeTable, "id", fakeTimeTableId);

        given(timeTableRepository.save(any())).willReturn(timeTable);
        timeTableService.createTimeTable(timeTableCreateDTO);

        given(timeTableRepository.findByUserId(fakeUserId)).willReturn(Collections.singletonList(timeTable));

        // whena
        TimeTableListDTO timeTableListDTO = timeTableService.findTimeTablesByMemberId(member.getId());

        // then
        TimeTableDTO timeTableResponseDTO = timeTableListDTO.getTimeTableList().get(0);
        assertThat(timeTableResponseDTO.getTimeTableId()).isEqualTo(timeTable.getId());
        assertThat(timeTableResponseDTO.getTableName()).isEqualTo(timeTable.getTableName().timeTableName());
    }

    @Test
    @DisplayName("????????? id??? ???????????? ????????? ????????? ????????????-2(?????? ?????????B: ????????? ???????????? 2??????(?????????) ??????)")
    public void findTImeTablesByMemberIdTest2() throws Exception {
        // given
        Member member = member();
        Long fakeUserId = 11L;
        ReflectionTestUtils.setField(member, "id", fakeUserId);
        given(memberRepository.findById(fakeUserId)).willReturn(Optional.of(member));

        // ????????? 2??? ??????
        TimeTableCreateDTO timeTableCreateDTO_1 = timeTableCreateDTO(member.getId(), "????????? ??????1");
        TimeTable timeTable1 = createTimeTable(timeTableCreateDTO_1, member);
        Long fakeTimeTableId1 = 3L;
        ReflectionTestUtils.setField(timeTable1, "id", fakeTimeTableId1);

        TimeTableCreateDTO timeTableCreateDTO_2 = timeTableCreateDTO(member.getId(), "????????? ??????2");
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
    @DisplayName("????????? id??? ???????????? ????????? ????????? ????????????-3(?????? ?????????C: ????????? ???????????? 0?????? ??????)")
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
    @DisplayName("????????? id??? ???????????? ????????? ????????? ????????????-4(?????? ?????????: ???????????? ?????? ????????? id)")
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
    @DisplayName("????????? ?????? ?????????-1(?????? ?????????)")
    public void deleteTimeTableTest1() throws Exception {
        // given
        Member member = member();
        Long fakeUserId = 11L;
        ReflectionTestUtils.setField(member, "id", fakeUserId);
        given(memberRepository.findById(fakeUserId)).willReturn(Optional.of(member));

        TimeTableCreateDTO timeTableCreateDTO = timeTableCreateDTO(member.getId(), "????????? ??????");
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
    @DisplayName("????????? ?????? ?????????-2(?????? ?????????A: ????????? id(X), ????????? id(O))")
    public void deleteTimeTableTest2() throws Exception {
        // given
        Member member = member();
        Long fakeUserId = 11L;
        ReflectionTestUtils.setField(member, "id", fakeUserId);
        given(memberRepository.findById(fakeUserId)).willReturn(Optional.of(member));

        TimeTableCreateDTO timeTableCreateDTO = timeTableCreateDTO(member.getId(), "????????? ??????");
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
    @DisplayName("????????? ?????? ?????????-3(?????? ?????????B: ????????? id(O), ????????? id(X))")
    public void deleteTimeTableTest3() throws Exception {
        // given
        Member member = member();
        Long fakeUserId = 11L;
        ReflectionTestUtils.setField(member, "id", fakeUserId);
        given(memberRepository.findById(fakeUserId)).willReturn(Optional.of(member));

        TimeTableCreateDTO timeTableCreateDTO = timeTableCreateDTO(member.getId(), "????????? ??????");
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
    @DisplayName("????????? ?????? ?????????-4(?????? ?????????C: ????????? id(X), ????????? id(X))")
    public void deleteTimeTableTest4() throws Exception {
        // given
        Member member = member();
        Long fakeUserId = 11L;
        ReflectionTestUtils.setField(member, "id", fakeUserId);
        given(memberRepository.findById(fakeUserId)).willReturn(Optional.of(member));

        TimeTableCreateDTO timeTableCreateDTO = timeTableCreateDTO(member.getId(), "????????? ??????");
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
    @DisplayName("????????? ???????????? ?????????-1(?????? ?????????)")
    public void updateTimeTableTest1() throws Exception {
        // given
        Member member = member();
        Long fakeUserId = 11L;
        ReflectionTestUtils.setField(member, "id", fakeUserId);
        given(memberRepository.findById(fakeUserId)).willReturn(Optional.of(member));

        TimeTableCreateDTO timeTableCreateDTO = timeTableCreateDTO(member.getId(), "????????? ??????");
        TimeTable timeTable = createTimeTable(timeTableCreateDTO, member);
        Long fakeTimeTableId = 3L;
        ReflectionTestUtils.setField(timeTable, "id", fakeTimeTableId);

        given(timeTableRepository.save(any())).willReturn(timeTable);
        given(timeTableRepository.findById(fakeTimeTableId)).willReturn(Optional.of(timeTable));

        timeTableService.createTimeTable(timeTableCreateDTO);

        // when
        final String NEW_TIMETABLE_NAME = "New ????????? ??????";
        TimeTableCreateDTO timeTableCreateDTOForUpdate = timeTableCreateDTO(member.getId(), NEW_TIMETABLE_NAME);
        timeTableService.updateTimeTable(timeTable.getId(), timeTableCreateDTOForUpdate);

        // then
        assertThat(timeTable.getTableName().timeTableName()).isEqualTo(NEW_TIMETABLE_NAME);
        assertThat(timeTable.getContent().tableContent()).isEqualTo(timeTableCreateDTO.getTimeTableContent());
    }

    @Test
    @DisplayName("????????? ???????????? ?????????-3(?????? ?????????: ?????? ?????? ?????? ?????? ?????????????????? ?????? ??????????????? ?????? ??????)")
    public void updateTimeTableTest2() throws Exception {
        // given
        Member member = member();
        Long fakeUserId = 11L;
        ReflectionTestUtils.setField(member, "id", fakeUserId);
        given(memberRepository.findById(fakeUserId)).willReturn(Optional.of(member));

        TimeTableCreateDTO timeTableCreateDTO = timeTableCreateDTO(member.getId(), "????????? ??????");
        TimeTable timeTable = createTimeTable(timeTableCreateDTO, member);
        Long fakeTimeTableId = 3L;
        ReflectionTestUtils.setField(timeTable, "id", fakeTimeTableId);

        given(timeTableRepository.save(any())).willReturn(timeTable);

        timeTableService.createTimeTable(timeTableCreateDTO);

        // when
        final String NEW_TIMETABLE_NAME = "New ????????? ??????";
        Long nonExistentTimeTableId = 0L;
        TimeTableCreateDTO timeTableCreateDTOForUpdate = timeTableCreateDTO(member.getId(), NEW_TIMETABLE_NAME);

        // then
        assertThrows(NoSuchTimeTableException.class, () ->
                timeTableService.updateTimeTable(nonExistentTimeTableId, timeTableCreateDTOForUpdate));
    }

    /**
     * ????????? ????????? ????????? ?????? for Test
     */
    private TimeTable createTimeTable(TimeTableCreateDTO timeTableCreateDTO, Member member) {
        return TimeTable.createTimeTable(
                TimeTableName.from(timeTableCreateDTO.getTimeTableName()),
                TimeTableContent.from(timeTableCreateDTO.getTimeTableContent()),
                TimeTablePeriod.between(timeTableCreateDTO.getTotalPeriodStart(), timeTableCreateDTO.getTotalPeriodEnd()),
                member);
    }


    /**
     * ????????? requestDTO ?????? ????????? ?????? for Test
     * -> parameter??? ?????? timeTableName??? ?????? ??? ?????? ?????? TimeTable ????????? ?????? ??? ?????? ?????? ????????????
     */
    private TimeTableCreateDTO timeTableCreateDTO(Long userId, String timeTableName) {
        return new TimeTableCreateDTO(
                userId,
                timeTableName,
                "????????? ?????? is ...",
                LocalDateTime.of(2022, 1, 1, 0, 0),
                LocalDateTime.of(2022, 1, 5, 21, 0)
        );
    }

    /**
     * Member ????????? ????????? ?????? for Test
     */
    private Member member() {
        return Member.builder().name(UserName.from("?????????"))
                .nickname(UserNickName.from("JJ"))
                .password(UserPassword.from("1234"))
                .email(UserEmail.from("abc123@naver.com"))
                .roleType(RoleType.USER)
                .profileImage(UserProfileImage.from("profile.url"))
                .birth(LocalDate.of(2020, 1, 1))
                .build();
    }


}