package team.backend.goWithMe.domain.auth.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.backend.goWithMe.domain.auth.application.MemberAuthService;
import team.backend.goWithMe.domain.auth.dto.LoginRequestDto;
import team.backend.goWithMe.global.common.TokenDTO;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class AuthController {

    private final MemberAuthService memberAuthService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginRequestDto requestDto) {
        return new ResponseEntity<>(
                memberAuthService.authorize(requestDto.getEmail(), requestDto.getPassword()), HttpStatus.OK);
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDTO> reissue(@Valid @RequestBody TokenDTO requestToken) {
        TokenDTO tokenDTO =
                memberAuthService.reissue(requestToken.getAccessToken(), requestToken.getRefreshToken());

        return ResponseEntity.ok(tokenDTO);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody TokenDTO tokenDTO) {
        memberAuthService.logout(tokenDTO.getRefreshToken(), tokenDTO.getAccessToken());
        return ResponseEntity.ok().build();
    }
}
