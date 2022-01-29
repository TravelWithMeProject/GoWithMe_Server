package team.backend.goWithMe.domain.member.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team.backend.goWithMe.domain.auth.details.MemberDetails;
import team.backend.goWithMe.domain.member.application.MemberManagementService;
import team.backend.goWithMe.domain.member.domain.persist.Member;
import team.backend.goWithMe.domain.member.dto.JoinRequestDTO;
import team.backend.goWithMe.domain.member.dto.JoinResponseDTO;
import team.backend.goWithMe.domain.member.dto.MemberInfoDTO;
import team.backend.goWithMe.domain.member.dto.UpdateDTO;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberManagementService memberManagementService;

    @PostMapping("/join")
    public ResponseEntity<JoinResponseDTO> create(@Valid @RequestBody JoinRequestDTO request) {
        MemberDetails details = request.toDetails();
        return new ResponseEntity<>(memberManagementService.create(details), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Long> modify(@Valid @RequestBody UpdateDTO updateDTO) {
        Member member = updateDTO.toEntity();
        return new ResponseEntity<>(memberManagementService.update(member), HttpStatus.OK);
    }
}
