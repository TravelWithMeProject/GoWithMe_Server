package team.backend.goWithMe.domain.favorite.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.backend.goWithMe.domain.favorite.domain.persist.Favorite;
import team.backend.goWithMe.domain.favorite.domain.persist.FavoriteRepository;
import team.backend.goWithMe.domain.favorite.dto.FavoriteCreateResponse;
import team.backend.goWithMe.domain.favorite.dto.FavoriteFindDTO;
import team.backend.goWithMe.domain.favorite.error.NotFountFavoriteException;
import team.backend.goWithMe.global.error.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    // create
    public FavoriteCreateResponse createFavorite(final Favorite favorite) {
        Favorite savedFavorite = favoriteRepository.save(favorite);

        return FavoriteCreateResponse.createResponse(savedFavorite);
    }

    // read
//    public FavoriteFindDTO findOne(final Long id) {
//        Favorite favorite = favoriteRepository.findById(id).orElseThrow(() -> {
//            throw new NotFountFavoriteException(ErrorCode.NOT_FOUND_FAVORITE);
//        });
//    }

    // update
    public void update(final Favorite favorite, final Long id) {
        Favorite findFavorite = favoriteRepository.findById(id).orElseThrow(() -> {
            throw new NotFountFavoriteException(ErrorCode.NOT_FOUND_FAVORITE);
        });

        findFavorite.updateFavorite(favorite);
    }
}
