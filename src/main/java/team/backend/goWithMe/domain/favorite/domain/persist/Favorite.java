package team.backend.goWithMe.domain.favorite.domain.persist;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.domain.favorite.domain.vo.Accommodation;
import team.backend.goWithMe.domain.favorite.domain.vo.FavoriteArrival;
import team.backend.goWithMe.domain.favorite.domain.vo.FavoritePeriod;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Favorite {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long id;

    @Embedded
    private FavoriteArrival favoriteArrival;

    @Embedded
    private Accommodation accommodation;

    @Embedded
    private FavoritePeriod favoritePeriod;

    private Favorite(final FavoriteArrival favoriteArrival, final Accommodation accommodation, final FavoritePeriod favoritePeriod) {
        this.favoriteArrival = favoriteArrival;
        this.accommodation = accommodation;
        this.favoritePeriod = favoritePeriod;
    }

    /**
     * 비즈니스 로직
     */
    public static Favorite createFavorite(final FavoriteArrival favoriteArrival, final Accommodation accommodation,
                                          final FavoritePeriod favoritePeriod) {
        return new Favorite(favoriteArrival, accommodation, favoritePeriod);
    }

    public void updateFavorite(final FavoriteArrival favoriteArrival, final Accommodation accommodation,
                               final FavoritePeriod favoritePeriod) {

        changeArrival(favoriteArrival);
        changeAccommodation(accommodation);
        changeFavoritePeriod(favoritePeriod);
    }

    private void changeArrival(FavoriteArrival favoriteArrival) {
        this.favoriteArrival = favoriteArrival;
    }

    private void changeAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    private void changeFavoritePeriod(FavoritePeriod favoritePeriod) {
        this.favoritePeriod = favoritePeriod;
    }
}
