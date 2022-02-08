package team.backend.goWithMe.domain.favorite.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.backend.goWithMe.domain.favorite.application.FavoriteService;
import team.backend.goWithMe.domain.favorite.domain.persist.Favorite;
import team.backend.goWithMe.domain.favorite.dto.FavoriteCreateRequest;
import team.backend.goWithMe.domain.favorite.dto.FavoriteCreateResponse;
import team.backend.goWithMe.domain.favorite.dto.FavoriteFindDTO;
import team.backend.goWithMe.domain.favorite.dto.FavoriteUpdateDTO;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/favorite")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    // create
    @PostMapping("/save")
    public ResponseEntity<FavoriteCreateResponse> create(@Valid @RequestBody FavoriteCreateRequest requestDTO) {

        Favorite favorite = requestDTO.toEntity();
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
            @Valid @RequestBody FavoriteUpdateDTO requestDTO,
            @PathVariable Long favoriteId) {

        Favorite favorite = requestDTO.toEntity();

        favoriteService.update(favorite, favoriteId);

        return ResponseEntity.ok().build();
    }
}
