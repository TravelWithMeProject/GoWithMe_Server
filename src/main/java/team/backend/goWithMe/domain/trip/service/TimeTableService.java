package team.backend.goWithMe.domain.trip.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.backend.goWithMe.domain.member.domain.persist.Member;
import team.backend.goWithMe.domain.member.domain.persist.MemberRepository;
import team.backend.goWithMe.domain.trip.domain.persist.Schedule;
import team.backend.goWithMe.domain.trip.domain.persist.TimeTable;
import team.backend.goWithMe.domain.trip.dto.request.TimeTableCreateDTO;
import team.backend.goWithMe.domain.trip.dto.response.TimeTableDTO;
import team.backend.goWithMe.domain.trip.dto.response.TimeTableListDTO;
import team.backend.goWithMe.domain.trip.error.exception.*;
import team.backend.goWithMe.domain.trip.repository.ScheduleRepository;
import team.backend.goWithMe.domain.trip.repository.TimeTableRepository;
import team.backend.goWithMe.global.error.exception.ErrorCode;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TimeTableService {

    private final TimeTableRepository timeTableRepository;
    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;

    public TimeTableDTO findTimeTable(Long memberId, Long timeTableId) {
        TimeTable timeTable = timeTableRepository.findById(timeTableId)
                .orElseThrow(() -> new NoSuchMemberException(ErrorCode.NO_SUCH_MEMBER_IN_TIMETABLE));
        if (!timeTable.getMember().getId().equals(memberId)) {
            throw new WrongTimeTableOwnerIdException(ErrorCode.WRONG_TIMETABLE_OWNER);
        }
        return new TimeTableDTO(
                timeTable.getId(),
                timeTable.getTableName().timeTableName(),
                timeTable.getContent().tableContent(),
                timeTable.getTotalPeriod().totalPeriodStart(),
                timeTable.getTotalPeriod().totalPeriodEnd()
        );
    }

    public TimeTableListDTO findTimeTablesByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchMemberException(ErrorCode.NO_SUCH_MEMBER_IN_TIMETABLE));
        TimeTableListDTO timeTableListDTO = new TimeTableListDTO();
        timeTableRepository
                .findByUserId(member.getId())
                .forEach(t -> {
                    TimeTableDTO timeTableDTO = new TimeTableDTO(
                            t.getId(),
                            t.getTableName().timeTableName(),
                            t.getContent().tableContent(),
                            t.getTotalPeriod().totalPeriodStart(),
                            t.getTotalPeriod().totalPeriodEnd()
                    );
                    timeTableListDTO.addTimeTable(timeTableDTO);
                });
        return timeTableListDTO;
    }

    @Transactional
    public Long createTimeTable(TimeTableCreateDTO dto) {
        Member member = memberRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NoSuchMemberException(ErrorCode.NO_SUCH_MEMBER_IN_TIMETABLE));
        TimeTable savedTimeTable = timeTableRepository.save(dto.toEntity(member));
        return savedTimeTable.getId();
    }

    @Transactional
    public void deleteTimeTable(Long memberId, Long timeTableId) {
        TimeTable timeTable = timeTableRepository.findById(timeTableId)
                .orElseThrow(() -> new NoSuchTimeTableException(ErrorCode.NO_SUCH_TIMETABLE));
        if (!timeTable.getMember().getId().equals(memberId)) {
            throw new WrongTimeTableOwnerIdException(ErrorCode.WRONG_TIMETABLE_OWNER);
        }
        scheduleRepository.deleteAll(scheduleRepository.findByTimeTableId(timeTableId));
        timeTableRepository.delete(timeTable);
    }

    @Transactional
    public void updateTimeTable(Long timeTableId, TimeTableCreateDTO dto) {
        Member member = memberRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NoSuchMemberException(ErrorCode.NO_SUCH_MEMBER_IN_TIMETABLE));
        TimeTable timeTable = timeTableRepository.findById(timeTableId)
                .orElseThrow(() -> new NoSuchTimeTableException(ErrorCode.NO_SUCH_TIMETABLE));

        validUpdateTimeTablePeriodWithSchedulePeriod(timeTableId, dto);
        timeTable.changeTimeTable(dto.toEntity(member));
    }

    /**
     * 기존 세부 일정이 있는 상태로 전체 일정을 변경한다고 할 때 최소한 기존 일정은 포함하는 선에서 조정이 이뤄져야하므로
     * 기존 세부 일정을 잘라먹지는 않는지 체크하는 검증 로직
     */
    private void validUpdateTimeTablePeriodWithSchedulePeriod(Long timeTableId, TimeTableCreateDTO dto) {
        List<Schedule> scheduleList = scheduleRepository.findByTimeTableIdOrderByPeriodStart(timeTableId);
        if (!scheduleList.isEmpty()) {
            // 세부일정 중 가장 빠른 것의 시작시간과 업데이트할 전체일정의 시작시간 비교
            Schedule earliestSchedule = scheduleList.get(0);
            if (dto.getTotalPeriodStart()
                    .isAfter(earliestSchedule.getSchedulePeriod().scheduleDetailStart())) {
                throw new TimeTablePeriodInvalidException(ErrorCode.PERIOD_MISMATCH_ERROR);
            }
            // 세부일정 중 가장 마지막(나중) 것의 종료시간과 업데이트할 전체일정의 종료시간 비교
            Schedule latestSchedule = scheduleList.get(scheduleList.size() - 1);
            if (dto.getTotalPeriodEnd()
                    .isBefore(latestSchedule.getSchedulePeriod().scheduleDetailEnd())) {
                throw new TimeTablePeriodInvalidException(ErrorCode.PERIOD_MISMATCH_ERROR);
            }
        }
    }
}