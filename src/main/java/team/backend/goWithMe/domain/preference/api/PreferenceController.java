package team.backend.goWithMe.domain.preference.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.backend.goWithMe.domain.preference.application.PreferenceService;
import team.backend.goWithMe.domain.preference.domain.persist.Preference;
import team.backend.goWithMe.domain.preference.dto.PreferenceCreateRequest;
import team.backend.goWithMe.domain.preference.dto.PreferenceCreateResponse;
import team.backend.goWithMe.domain.preference.dto.PreferenceUpdateDTO;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/favorite")
@RequiredArgsConstructor
public class PreferenceController {

    private final PreferenceService favoriteService;

    // create
    @PostMapping("/save")
    public ResponseEntity<PreferenceCreateResponse> create(@Valid @RequestBody PreferenceCreateRequest requestDTO) {

        Preference favorite = requestDTO.toEntity();
        return ResponseEntity.ok(favoriteService.createFavorite(favorite));
    }

    // Read
//    @GetMapping("/{favoriteId}")
//    public ResponseEntity<FavoriteFindDTO> findFavorite(@PathVariable Long favoriteId) {
//        return new ResponseEntity<>(favoriteService.findOne(favoriteId), HttpStatus.OK);
//    }

    // update
    @PutMapping("{favoriteId}")
    public ResponseEntity<Void> modify(
            @Valid @RequestBody PreferenceUpdateDTO requestDTO,
            @PathVariable Long favoriteId) {

        Preference favorite = requestDTO.toEntity();

        favoriteService.update(favorite, favoriteId);

        return ResponseEntity.ok().build();
    }
}
