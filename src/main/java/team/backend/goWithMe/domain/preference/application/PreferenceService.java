package team.backend.goWithMe.domain.preference.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.backend.goWithMe.domain.preference.domain.persist.Preference;
import team.backend.goWithMe.domain.preference.domain.persist.PreferenceRepository;
import team.backend.goWithMe.domain.preference.dto.PreferenceCreateResponse;
import team.backend.goWithMe.domain.preference.dto.PreferenceFindDTO;
import team.backend.goWithMe.domain.preference.error.NotFountFavoriteException;
import team.backend.goWithMe.domain.member.domain.persist.Member;
import team.backend.goWithMe.domain.member.domain.persist.MemberRepository;
import team.backend.goWithMe.domain.member.domain.vo.UserEmail;
import team.backend.goWithMe.domain.member.error.exception.MemberNotFoundException;
import team.backend.goWithMe.global.error.exception.ErrorCode;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PreferenceService {

    private final PreferenceRepository preferenceRepository;
    private final MemberRepository memberRepository;

    // create
    public PreferenceCreateResponse createFavorite(final Preference preference, final UserEmail email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        Preference newPreference = Preference.createPreference(preference.getPreferenceArrival(), preference.getAccommodation(),
                preference.getPreferencePeriod(), member);

        return PreferenceCreateResponse.createResponse(preferenceRepository.save(newPreference));
    }

    // read
    public PreferenceFindDTO findOne(final UserEmail email) {
        Preference preference = getPreference(email);
        return PreferenceFindDTO.from(preference);
    }

    // update
    public void update(final Preference preference, final UserEmail email) {
        Preference originPreference = getPreference(email);
        originPreference.updateFavorite(preference);
    }
    // delete
    public void delete(UserEmail email) {
        Preference preference = getPreference(email);
        preferenceRepository.delete(preference);
    }

    private Preference getPreference(UserEmail email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        return preferenceRepository.findById(member.getPreference().getId()).orElseThrow(
                () -> new NotFountFavoriteException(ErrorCode.NOT_FOUND_FAVORITE));
    }
}
