package team.backend.goWithMe.domain.favorite.domain.persist;

import lombok.Getter;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.domain.favorite.domain.vo.Accommodation;
import team.backend.goWithMe.domain.favorite.domain.vo.FavoriteArrival;
import team.backend.goWithMe.domain.favorite.domain.vo.FavoritePeriod;
import team.backend.goWithMe.domain.member.domain.persist.Member;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Favorite {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long id;

    @Embedded
    private FavoriteArrival favoriteArrival; // 도착지

    @Embedded
    private Accommodation accommodation; // 숙박 유형

    @Embedded
    private FavoritePeriod favoritePeriod; // 앞으로 여행 계획

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

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

    public void updateFavorite(final Favorite favorite) {

        changeArrival(favorite.getFavoriteArrival());
        changeAccommodation(favorite.getAccommodation());
        changeFavoritePeriod(favorite.getFavoritePeriod());
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
