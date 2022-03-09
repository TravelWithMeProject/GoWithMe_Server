package team.backend.goWithMe.domain.member.api;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import team.backend.goWithMe.domain.member.application.MemberManagementService;
import team.backend.goWithMe.domain.member.domain.persist.Member;
import team.backend.goWithMe.domain.member.domain.vo.UserEmail;
import team.backend.goWithMe.domain.member.domain.vo.UserName;
import team.backend.goWithMe.domain.member.dto.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@Api("회원 관리 API")
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberManagementController {

    private final MemberManagementService memberManagementService;

    @PostMapping("/join")
    @ApiOperation(value = "회원 가입", notes = "회원 정보를 입력받아 저장한다.")
    @ApiParam(name = "회원 가입 데이터 전달 DTO", value = "요청 들어온 회원 정보")
    public ResponseEntity<JoinResponseDTO> create(@Valid @RequestBody JoinRequestDTO request) {
        Member member = request.toEntity();

        URI createdMemberURI = URI.create(String.format("/api/v1/member/%d", member.getId()));

        return ResponseEntity.created(createdMemberURI).body(memberManagementService.create(member));
    }

    @PatchMapping
    @ApiOperation(value = "회원 수정", notes = "회원 수정 정보를 입력 받아 변경한다.")
    public ResponseEntity<Void> modify(
            @ApiParam(name = "변경된 회원 데이터")
            @Valid @RequestBody MemberUpdateDTO updateDTO) {
        Member member = updateDTO.toEntity();

        memberManagementService.update(member, UserEmail.from(getEmail()));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findByEmail")
    @ApiOperation(value = "회원 조회", notes = "회원 정보를 보여주는 API")
    public ResponseEntity<MemberResponseDTO> findByEmail() {
        return ResponseEntity.ok(memberManagementService.findOne(UserEmail.from(getEmail())));
    }

    @DeleteMapping
    @ApiOperation(value = "회원 삭제", notes = "회원 정보를 삭제한다.")
    public ResponseEntity<Void> delete() {
        memberManagementService.delete(UserEmail.from(getEmail()));
        return ResponseEntity.noContent().build();
    }

    // 회원 검색 (JPQL)
    @GetMapping("/search/{name}")
    public ResponseEntity<List<FindAllResponse>> searchMember(
            @PathVariable String name,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(memberManagementService.searchMember(UserName.from(name), pageable));
    }


    // 회원 검색창 -> 자기랑 선호도가 맞는 회원을 갖고와야함 -> 회원 검색
    @GetMapping
    public ResponseEntity<List<FindAllResponse>> findAll(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(memberManagementService.findAll(UserEmail.from(getEmail()), pageable));
    }

    private String getEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
