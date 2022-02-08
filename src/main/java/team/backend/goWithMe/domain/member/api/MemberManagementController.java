package team.backend.goWithMe.domain.member.api;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.backend.goWithMe.domain.member.application.MemberManagementService;
import team.backend.goWithMe.domain.member.domain.persist.Member;
import team.backend.goWithMe.domain.member.dto.*;

import javax.validation.Valid;

@RestController
@Api("회원 관리 API")
@RequestMapping("/api/v1/member/")
@RequiredArgsConstructor
public class MemberManagementController {

    private final MemberManagementService memberManagementService;

    @PostMapping("join")
    @ApiOperation(value = "회원 가입", notes = "회원 정보를 입력받아 저장한다.")
    @ApiParam(name = "회원 가입 데이터 전달 DTO", value = "요청 들어온 회원 정보")
    public ResponseEntity<JoinResponseDTO> create(@Valid @RequestBody JoinRequestDTO request) {
        Member member = request.toEntity();
        return new ResponseEntity<>(memberManagementService.create(member), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @ApiOperation(value = "회원 수정", notes = "회원 수정 정보를 입력 받아 변경한다.")
    public ResponseEntity<Void> modify(
            @ApiParam(name = "회원 아이디", value = "회원 고유 식별 값")
            @PathVariable Long id,
            @ApiParam(name = "회원 변경 데이터 전달 DTO", value = "요청 들어온 회원 변경 정보")
            @Valid @RequestBody MemberUpdateDTO updateDTO) {
        Member member = updateDTO.toEntity();
        memberManagementService.update(member, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    @ApiOperation(value = "회원 조회", notes = "회원 정보를 보여주는 API")
    @ApiParam(name = "회원 아이디", value = "회원 고유 식별값")
    public ResponseEntity<MemberResponseDTO> findMember(@PathVariable Long id) {
        return new ResponseEntity<>(memberManagementService.findOne(id), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "회원 삭제", notes = "회원 정보를 삭제한다.")
    @ApiParam(name = "회원 아이디", value = "회원 고유 식별 값")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        memberManagementService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 회원 검색창 -> 자기랑 선호도가 맞는 회원을 갖고와야함 -> 회원 검색

    // 회원 검색 (QueryDSL)

    // 선호도 (?)
}
