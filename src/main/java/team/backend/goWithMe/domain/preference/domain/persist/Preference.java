package team.backend.goWithMe.domain.preference.domain.persist;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.domain.preference.domain.vo.Accommodation;
import team.backend.goWithMe.domain.preference.domain.vo.FavoriteArrival;
import team.backend.goWithMe.domain.preference.domain.vo.FavoritePeriod;
import team.backend.goWithMe.domain.member.domain.persist.Member;
import team.backend.goWithMe.global.common.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Preference extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "preference_id")
    private Long id;

    @Embedded
    private FavoriteArrival favoriteArrival; // 도착지

    @Embedded
    private Accommodation accommodation; // 숙박 유형

    @Embedded
    private FavoritePeriod favoritePeriod; // 앞으로 여행 계획

    private Preference(final FavoriteArrival favoriteArrival,
                       final Accommodation accommodation, final FavoritePeriod favoritePeriod) {
        this.favoriteArrival = favoriteArrival;
        this.accommodation = accommodation;
        this.favoritePeriod = favoritePeriod;
    }

    /**
     * 비즈니스 로직
     */
    public static Preference createSurvey(final FavoriteArrival favoriteArrival, final Accommodation accommodation,
                                          final FavoritePeriod favoritePeriod) {
        return new Preference(favoriteArrival, accommodation, favoritePeriod);
    }

    public void updateFavorite(final Preference favorite) {

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
