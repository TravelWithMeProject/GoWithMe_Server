package team.backend.goWithMe.domain.preference.domain.persist;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.domain.preference.domain.vo.Accommodation;
import team.backend.goWithMe.domain.preference.domain.vo.PreferenceArrival;
import team.backend.goWithMe.domain.preference.domain.vo.PreferencePeriod;
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
    private PreferenceArrival preferenceArrival; // 도착지

    @Embedded
    private Accommodation accommodation; // 숙박 유형

    @Embedded
    private PreferencePeriod preferencePeriod; // 앞으로 여행 계획

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private Preference(final PreferenceArrival favoriteArrival,
                       final Accommodation accommodation, final PreferencePeriod favoritePeriod) {
        this.preferenceArrival = favoriteArrival;
        this.accommodation = accommodation;
        this.preferencePeriod = favoritePeriod;
    }

    private Preference(final PreferenceArrival favoriteArrival, final Accommodation accommodation,
                       final PreferencePeriod favoritePeriod, final Member member) {
        this.preferenceArrival = favoriteArrival;
        this.accommodation = accommodation;
        this.preferencePeriod = favoritePeriod;
        addMember(member);
    }

    /**
     * 비즈니스 로직
     */
    public static Preference createSurvey(final PreferenceArrival favoriteArrival, final Accommodation accommodation,
                                          final PreferencePeriod favoritePeriod) {
        return new Preference(favoriteArrival, accommodation, favoritePeriod);
    }

    public static Preference createPreference(final PreferenceArrival favoriteArrival, final Accommodation accommodation,
                                              final PreferencePeriod favoritePeriod, final Member member) {

        return new Preference(favoriteArrival, accommodation, favoritePeriod, member);
    }

    public void updateFavorite(final Preference preference) {

        changeArrival(preference.getPreferenceArrival());
        changeAccommodation(preference.getAccommodation());
        changeFavoritePeriod(preference.getPreferencePeriod());
    }

    private void changeArrival(PreferenceArrival favoriteArrival) {
        this.preferenceArrival = favoriteArrival;
    }

    private void changeAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    private void changeFavoritePeriod(PreferencePeriod favoritePeriod) {
        this.preferencePeriod = favoritePeriod;
    }

    private void addMember(final Member member) {
        this.member = member;
        member.addPreference(this);
    }
}
