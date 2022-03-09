package team.backend.goWithMe.domain.preference.domain.util;

import team.backend.goWithMe.domain.preference.domain.persist.Preference;
import team.backend.goWithMe.domain.preference.domain.vo.Accommodation;
import team.backend.goWithMe.domain.preference.domain.vo.PreferenceArrival;
import team.backend.goWithMe.domain.preference.domain.vo.PreferencePeriod;

import java.time.LocalDate;

public class GivenPreference {

    public static final PreferenceArrival givenArrival = PreferenceArrival.from("JAPAN");
    public static final Accommodation givenAccommodation = Accommodation.from("HOTEL");
    public static final PreferencePeriod givenPeriod = PreferencePeriod.of(LocalDate.of(2022, 1, 2),
            LocalDate.of(2022, 2, 2));
}
