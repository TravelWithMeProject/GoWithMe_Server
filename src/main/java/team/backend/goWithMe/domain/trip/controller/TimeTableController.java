package team.backend.goWithMe.domain.trip.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.backend.goWithMe.domain.member.domain.persist.Member;
import team.backend.goWithMe.domain.member.domain.persist.RoleType;
import team.backend.goWithMe.domain.member.domain.vo.*;
import team.backend.goWithMe.domain.trip.dto.request.TimeTableCreateDTO;
import team.backend.goWithMe.domain.trip.dto.response.TimeTableDTO;
import team.backend.goWithMe.domain.trip.dto.response.TimeTableIdDTO;
import team.backend.goWithMe.domain.trip.dto.response.TimeTableListDTO;
import team.backend.goWithMe.domain.trip.dto.response.TimeTableSuccessDTO;
import team.backend.goWithMe.domain.trip.repository.MemberRepository;
import team.backend.goWithMe.domain.trip.service.TimeTableService;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@RestController
@Tag(name = "TimeTableController", description = "시간표(전체 일정) API")
@RequiredArgsConstructor
@RequestMapping("/timeTable")
public class TimeTableController {

    private final TimeTableService timeTableService;

    //===== Dummy Data for Postman Test =====//
    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
        memberRepository.save(Member
                .builder()
                .name(UserName.from("정의재"))
                .birth(LocalDate.of(2021, 1, 1))
                .nickname(UserNickName.from("JJ"))
                .email(UserEmail.from("abc@naver.com"))
                .password(UserPassword.from("1234"))
                .roleType(RoleType.USER)
                .profileImage(UserProfileImage.from("sampleURL"))
                .build());
    }
    //======================//

    @GetMapping("/{memberId}/{timeTableId}")
    @ApiOperation(value = "특정 시간표 가져오기", notes = "시간표 id값을 통해 해당 시간표를 가져오는 API")
    public ResponseEntity<TimeTableDTO> timeTable(
            @ApiParam(value = "사용자 id", required = true, example = "1")
            @PathVariable Long memberId,
            @ApiParam(value = "시간표 id", required = true, example = "3")
            @PathVariable Long timeTableId) {
        TimeTableDTO dto = timeTableService.findTimeTable(memberId, timeTableId);

        return new ResponseEntity<>(dto, header(), HttpStatus.OK);
    }

    @GetMapping("/{memberId}")
    @ApiOperation(value = "사용자의 모든 시간표 가져오기", notes = "사용자 id값을 통해 해당 사용자가 가지고 있는 모든 시간표를 가져오는 API")
    public ResponseEntity<TimeTableListDTO> timeTableList(
            @ApiParam(value = "사용자 id", required = true, example = "2")
            @PathVariable Long memberId) {
        TimeTableListDTO dto = timeTableService.findTimeTablesByMemberId(memberId);

        return new ResponseEntity<>(dto, header(), HttpStatus.OK);
    }

    @PostMapping("/create")
    @ApiOperation(value = "시간표 생성", notes = "시간표(전체 일정)를 생성하는 API")
    public ResponseEntity<TimeTableIdDTO> createTimeTable(@RequestBody TimeTableCreateDTO dto) {
        Long savedTimeTableId = timeTableService.saveTimeTable(dto);
        TimeTableIdDTO responseDTO = new TimeTableIdDTO(true, savedTimeTableId);

        return new ResponseEntity<>(responseDTO, header(), HttpStatus.OK);
    }

    @PutMapping("/{timeTableId}")
    @ApiOperation(value = "시간표 업데이트", notes = "시간표(전체 일정)를 업데이트하는 API")
    public ResponseEntity<TimeTableIdDTO> updateTimeTable(
            @ApiParam(value = "시간표 id", required = true, example = "2")
            @PathVariable Long timeTableId,
            @RequestBody TimeTableCreateDTO dto) {
        Long updatedTimeTableId = timeTableService.updateTimeTable(timeTableId, dto);
        TimeTableIdDTO idDTO = new TimeTableIdDTO(true, updatedTimeTableId);

        return new ResponseEntity<>(idDTO, header(), HttpStatus.OK);
    }

    @DeleteMapping("/{memberId}/{timeTableId}")
    @ApiOperation(value = "시간표 삭제", notes = "시간표(전체 일정)를 삭제하는 API")
    public ResponseEntity<TimeTableSuccessDTO> deleteTimeTable(
            @ApiParam(value = "사용자 id", required = true, example = "1")
            @PathVariable Long memberId,
            @ApiParam(value = "시간표 id", required = true, example = "3")
            @PathVariable Long timeTableId) {
        TimeTableSuccessDTO dto = timeTableService.deleteTimeTable(memberId, timeTableId);

        return new ResponseEntity<>(dto, header(), HttpStatus.OK);
    }

    private HttpHeaders header() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return httpHeaders;
    }

}