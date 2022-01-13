package team.backend.goWithMe.domain.favorite.domain.persist;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team.backend.goWithMe.domain.favorite.domain.vo.Accommodation;
import team.backend.goWithMe.domain.favorite.domain.vo.FavoriteArrival;
import team.backend.goWithMe.domain.favorite.domain.vo.FavoritePeriod;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class favoriteTest {

    @Test
    @DisplayName("선호도 조사를 추후에 수정할 수 있다.")
    void updateFavorite() {
        // given
        FavoriteArrival givenArrival = FavoriteArrival.from("U.S.A");
        Accommodation givenHome = Accommodation.from("hotel");
        LocalDate givenStart = LocalDate.of(2022, 1, 2);
        LocalDate givenEnd = LocalDate.of(2022, 2, 2);
        FavoritePeriod givenPeriod = FavoritePeriod.of(givenStart, givenEnd);

        Favorite favorite = Favorite.createFavorite(givenArrival, givenHome, givenPeriod);

        // when
        favorite.updateFavorite(FavoriteArrival.from("canada"), Accommodation.from("guestHouse"), givenPeriod);

        // then
        assertThat(favorite.getFavoriteArrival()).isEqualTo(FavoriteArrival.from("canada"));
        assertThat(favorite.getAccommodation()).isEqualTo(Accommodation.from("guestHouse"));
    }
}