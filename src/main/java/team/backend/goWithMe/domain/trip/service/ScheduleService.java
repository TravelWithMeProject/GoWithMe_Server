package team.backend.goWithMe.domain.trip.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.backend.goWithMe.domain.trip.domain.persist.Schedule;
import team.backend.goWithMe.domain.trip.domain.persist.TimeTable;
import team.backend.goWithMe.domain.trip.domain.vo.ScheduleContent;
import team.backend.goWithMe.domain.trip.domain.vo.ScheduleCost;
import team.backend.goWithMe.domain.trip.domain.vo.SchedulePeriod;
import team.backend.goWithMe.domain.trip.domain.vo.ScheduleTitle;
import team.backend.goWithMe.domain.trip.dto.request.ScheduleCreateDTO;
import team.backend.goWithMe.domain.trip.dto.response.ScheduleDTO;
import team.backend.goWithMe.domain.trip.dto.response.ScheduleListDTO;
import team.backend.goWithMe.domain.trip.error.exception.NoSuchScheduleException;
import team.backend.goWithMe.domain.trip.error.exception.NoSuchTimeTableException;
import team.backend.goWithMe.domain.trip.error.exception.SchedulePeriodInvalidException;
import team.backend.goWithMe.domain.trip.error.exception.WrongScheduleOwnerException;
import team.backend.goWithMe.domain.trip.repository.ScheduleRepository;
import team.backend.goWithMe.domain.trip.repository.TimeTableRepository;
import team.backend.goWithMe.global.error.exception.ErrorCode;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final TimeTableRepository timeTableRepository;

    public ScheduleDTO findOneSchedule(Long timeTableId, Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NoSuchScheduleException(ErrorCode.NO_SUCH_SCHEDULE));
        validateScheduleOwner(timeTableId, schedule.getTimeTable().getId());

        return new ScheduleDTO(schedule.getId(),
                schedule.getScheduleTitle().scheduleTitle(),
                schedule.getScheduleContent().scheduleContent(),
                schedule.getSchedulePeriod().scheduleDetailStart(),
                schedule.getSchedulePeriod().scheduleDetailEnd(),
                schedule.getScheduleCost().amount());
    }

    @Transactional
    public Long createSchedule(ScheduleCreateDTO dto) {
        TimeTable timeTable = timeTableRepository.findById(dto.getTimeTableId())
                .orElseThrow(() -> new NoSuchTimeTableException(ErrorCode.NO_SUCH_TIMETABLE));
        validateNewScheduleWithOtherSchedule(dto);
        Schedule savedSchedule = scheduleRepository.save(dto.toEntity(timeTable));
        return savedSchedule.getId();
    }

    public ScheduleListDTO findSchedulesByTimeTableId(Long timeTableId) {
        List<Schedule> scheduleList = scheduleRepository.findByTimeTableId(timeTableId);
        ScheduleListDTO scheduleListDTO = new ScheduleListDTO();
        scheduleList.forEach(schedule -> {
            ScheduleDTO scheduleDTO = new ScheduleDTO(schedule.getId(), schedule.getScheduleTitle().scheduleTitle(),
                    schedule.getScheduleContent().scheduleContent(), schedule.getSchedulePeriod().scheduleDetailStart(),
                    schedule.getSchedulePeriod().scheduleDetailEnd(), schedule.getScheduleCost().amount());
            scheduleListDTO.addSchedule(scheduleDTO);
        });

        return scheduleListDTO;
    }

    @Transactional
    public void updateSchedule(Long scheduleId, ScheduleCreateDTO dto) {
        TimeTable timeTable = timeTableRepository.findById(dto.getTimeTableId())
                .orElseThrow(() -> new NoSuchTimeTableException(ErrorCode.NO_SUCH_TIMETABLE));
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NoSuchScheduleException(ErrorCode.NO_SUCH_SCHEDULE));
        validateNewScheduleWithOtherSchedule(dto);
        schedule.changeSchedule(dto.toEntity(timeTable));
    }

    @Transactional
    public void deleteSchedule(Long timeTableId, Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NoSuchScheduleException(ErrorCode.NO_SUCH_SCHEDULE));
        validateScheduleOwner(timeTableId, schedule.getId());

        scheduleRepository.delete(schedule);
    }

    private void validateNewScheduleWithOtherSchedule(ScheduleCreateDTO dto) {
        List<Schedule> scheduleList = scheduleRepository.findByTimeTableIdOrderByPeriodStart(dto.getTimeTableId());
        LocalDateTime newPeriodStart = dto.getDetailPeriodStart();
        LocalDateTime newPeriodEnd = dto.getDetailPeriodEnd();

        scheduleList.forEach(schedule -> {
            LocalDateTime existingPeriodStart = schedule.getSchedulePeriod().scheduleDetailStart();
            LocalDateTime existingPeriodEnd = schedule.getSchedulePeriod().scheduleDetailEnd();

            // 새 세부일정은 기존 세부일정과 조금이라도 겹치면 안됨
            // [..]: 새 세부일정, <..>: 기존 세부일정 ( 1)'[..]..<..>' or 2)'<..>..[..]'이어야 정상, 나머지 경우는 다 throw )
            // 1) '[..]..<..>' 이 아니면,
            if (!(newPeriodStart.isBefore(existingPeriodStart) && newPeriodEnd.isBefore(existingPeriodStart))) {
                // 2) '<..>..[..]' 이 아니면,
                if (!(newPeriodStart.isAfter(existingPeriodEnd) && newPeriodEnd.isAfter(existingPeriodEnd))) {
                    // 3) '<..[..>..]' 4) '[..<..]..>' 5) '[..<..>..]' 6) '<..[..]..>' 인 경우 throw
                    throw new SchedulePeriodInvalidException(ErrorCode.PERIOD_MISMATCH_ERROR);
                }
            }
        });
    }

    private void validateScheduleOwner(Long timeTableId, Long timeTableIdBySchedule) {
        if (!timeTableIdBySchedule.equals(timeTableId)) {
            throw new WrongScheduleOwnerException(ErrorCode.WRONG_SCHEDULE_OWNER);
        }
    }
}
