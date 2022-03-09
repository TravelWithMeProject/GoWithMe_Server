package team.backend.goWithMe.domain.preference.domain.persist;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team.backend.goWithMe.domain.preference.domain.vo.PreferenceArrival;
import team.backend.goWithMe.domain.preference.error.OverPeriodException;
import team.backend.goWithMe.domain.preference.domain.vo.Accommodation;
import team.backend.goWithMe.domain.preference.domain.vo.PreferencePeriod;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class PreferenceTest {

    @Test
    @DisplayName("선호도 조사를 추후에 수정할 수 있다.")
    void updateFavorite() {
        // given
        PreferencePeriod givenPeriod = PreferencePeriod.of(LocalDate.of(2022, 1, 2),
                LocalDate.of(2022, 2, 2));

        Preference favorite = Preference.createSurvey(PreferenceArrival.from("U.S.A"),
                Accommodation.from("hotel"), givenPeriod);

        Preference update = Preference.createSurvey(PreferenceArrival.from("canada"),
                Accommodation.from("guestHouse"), givenPeriod);

        // when
        favorite.updateFavorite(update);

        // then
        assertThat(favorite.getPreferenceArrival().preferenceArrival()).isEqualTo("canada");
        assertThat(favorite.getAccommodation().accommodation()).isEqualTo("guestHouse");
    }

    @Test
    @DisplayName("여행 시작기간이 종료기간보다 길면 WrongPeriodException이 발생한다.")
    void wrongPeriod() {
        //given
        LocalDate givenStart = LocalDate.of(2022, 3, 2);
        LocalDate givenEnd = LocalDate.of(2022, 2, 2);

        // then
        assertThrows(OverPeriodException.class,
                () -> PreferencePeriod.of(givenStart, givenEnd));
    }
}