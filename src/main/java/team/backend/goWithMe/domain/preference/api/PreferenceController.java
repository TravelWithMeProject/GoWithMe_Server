package team.backend.goWithMe.domain.preference.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import team.backend.goWithMe.domain.member.domain.vo.UserEmail;
import team.backend.goWithMe.domain.preference.application.PreferenceService;
import team.backend.goWithMe.domain.preference.domain.persist.Preference;
import team.backend.goWithMe.domain.preference.dto.PreferenceCreateRequest;
import team.backend.goWithMe.domain.preference.dto.PreferenceCreateResponse;
import team.backend.goWithMe.domain.preference.dto.PreferenceFindDTO;
import team.backend.goWithMe.domain.preference.dto.PreferenceUpdateDTO;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/preference")
@RequiredArgsConstructor
public class PreferenceController {

    private final PreferenceService preferenceService;

    // create
    @PostMapping("/save")
    @ApiOperation(value = "선호도 조사 설문지 생성", notes = "설문지를 하나 작성하여 저장한다.")
    public ResponseEntity<PreferenceCreateResponse> create(
            @ApiParam(name = "생성된 설문지 데이터")
            @Valid @RequestBody PreferenceCreateRequest requestDTO) {

        return ResponseEntity.created(URI.create("/api/v1/preference"))
                .body(preferenceService.createFavorite(requestDTO.toEntity(), UserEmail.from(getEmail())));
    }

    // Read
    @GetMapping
    @ApiOperation(value = "설문지 조회", notes = "회원의 설문지를 조회한다.")
    public ResponseEntity<PreferenceFindDTO> findPreference() {
        return ResponseEntity.ok(preferenceService.findOne(UserEmail.from(getEmail())));
    }

    // update
    @PatchMapping
    @ApiOperation(value = "설문지 업데이트", notes = "회원의 설문지를 업데이트한다.")
    public ResponseEntity<Void> update(@Valid @RequestBody PreferenceUpdateDTO requestDTO) {
        Preference favorite = requestDTO.toEntity();
        preferenceService.update(favorite, UserEmail.from(getEmail()));
        return ResponseEntity.ok().build();
    }

    // delete
    @DeleteMapping
    @ApiOperation(value = "설문지 삭제", notes = "회원의 설문지를 삭제한다.")
    public ResponseEntity<Void> delete() {
        preferenceService.delete(UserEmail.from(getEmail()));
        return ResponseEntity.noContent().build();
    }

    private String getEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
