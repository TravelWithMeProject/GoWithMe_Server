package team.backend.goWithMe.domain.preference.domain.persist;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team.backend.goWithMe.domain.preference.error.OverPeriodException;
import team.backend.goWithMe.domain.preference.domain.vo.Accommodation;
import team.backend.goWithMe.domain.preference.domain.vo.FavoriteArrival;
import team.backend.goWithMe.domain.preference.domain.vo.FavoritePeriod;

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

        Preference favorite = Preference.createSurvey(givenArrival, givenHome, givenPeriod);

        FavoriteArrival updateArrival = FavoriteArrival.from("canada");
        Accommodation updateAccommodation = Accommodation.from("guestHouse");

        Preference update = Preference.createSurvey(updateArrival, updateAccommodation, givenPeriod);

        // when
        favorite.updateFavorite(update);

        // then
        assertThat(favorite.getFavoriteArrival().favoriteArrival()).isEqualTo("canada");
        assertThat(favorite.getAccommodation().accommodation()).isEqualTo("guestHouse");
    }

    @Test
    @DisplayName("여행 시작기간이 종료기간보다 길면 WrongPeriodException이 발생한다.")
    void wrongPeriod() {
        //given
        FavoriteArrival givenArrival = FavoriteArrival.from("U.S.A");
        Accommodation givenHome = Accommodation.from("hotel");
        LocalDate givenStart = LocalDate.of(2022, 3, 2);
        LocalDate givenEnd = LocalDate.of(2022, 2, 2);
        FavoritePeriod givenPeriod = FavoritePeriod.of(givenStart, givenEnd);

        // then
        assertThrows(OverPeriodException.class, () -> {
            Preference favorite = Preference.createSurvey(givenArrival, givenHome, givenPeriod);
        });
    }
}