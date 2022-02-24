package team.backend.goWithMe.domain.trip.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.backend.goWithMe.domain.trip.dto.response.*;
import team.backend.goWithMe.domain.trip.dto.request.ScheduleCreateDTO;
import team.backend.goWithMe.domain.trip.service.ScheduleService;

import javax.validation.Valid;

@RestController
@Api("스케줄(세부일정) 관련 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/{timeTableId}/{scheduleId}")
    @ApiOperation(value = "특정 세부일정 가져오기", notes = "시간표 id값을 통해 해당 시간표를 가져오는 API")
    public ResponseEntity<ScheduleDTO> schedule(
            @ApiParam(value = "시간표 id", required = true, example = "3")
            @PathVariable Long timeTableId,
            @ApiParam(value = "세부 일정 id", required = true, example = "1")
            @PathVariable Long scheduleId) {
        return new ResponseEntity<>(scheduleService.findOneSchedule(timeTableId, scheduleId), HttpStatus.OK);
    }

    @GetMapping("/{timeTableId}")
    @ApiOperation(value = "사용자의 모든 세부일정(스케줄) 가져오기", notes = "시간표(전체일정) id값을 통해 해당 시간표가 가지고 있는 모든 스케줄(세부일정)을 가져오는 API")
    public ResponseEntity<ScheduleListDTO> scheduleList(
            @ApiParam(value = "시간표 id", required = true, example = "2")
            @PathVariable Long timeTableId) {
        return new ResponseEntity<>(scheduleService.findSchedulesByTimeTableId(timeTableId), HttpStatus.OK);
    }

    @PostMapping("/create")
    @ApiOperation(value = "세부일정 생성", notes = "스케줄(세부 일정)를 생성하는 API")
    public ResponseEntity<ScheduleIdDTO> createSchedule(@RequestBody @Valid ScheduleCreateDTO dto) {
        return new ResponseEntity<>(new ScheduleIdDTO(true, scheduleService.createSchedule(dto)), HttpStatus.OK);
    }

    @DeleteMapping("/{timeTableId}/{scheduleId}")
    @ApiOperation(value = "세부일정 삭제", notes = "시간표(전체 일정)를 삭제하는 API")
    public ResponseEntity<Void> deleteSchedule(
            @ApiParam(value = "시간표 id", required = true, example = "3")
            @PathVariable Long timeTableId,
            @ApiParam(value = "세부 일정 id", required = true, example = "1")
            @PathVariable Long scheduleId) {
        scheduleService.deleteSchedule(timeTableId, scheduleId);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{scheduleId}")
    @ApiOperation(value = "세부일정 업데이트", notes = "세부일정(스케줄)을 업데이트하는 API")
    public ResponseEntity<Void> updateSchedule(
            @ApiParam(value = "세부일정 id", required = true, example = "2")
            @PathVariable Long scheduleId,
            @RequestBody @Valid ScheduleCreateDTO dto) {
        scheduleService.updateSchedule(scheduleId, dto);
        return ResponseEntity.ok().build();
    }
}
