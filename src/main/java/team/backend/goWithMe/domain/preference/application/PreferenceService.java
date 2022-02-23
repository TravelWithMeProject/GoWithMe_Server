package team.backend.goWithMe.domain.preference.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.backend.goWithMe.domain.preference.domain.persist.Preference;
import team.backend.goWithMe.domain.preference.domain.persist.PreferenceRepository;
import team.backend.goWithMe.domain.preference.dto.PreferenceCreateResponse;
import team.backend.goWithMe.domain.preference.error.NotFountFavoriteException;
import team.backend.goWithMe.domain.member.domain.persist.Member;
import team.backend.goWithMe.domain.member.domain.persist.MemberRepository;
import team.backend.goWithMe.domain.member.domain.vo.UserEmail;
import team.backend.goWithMe.domain.member.error.exception.MemberNotFoundException;
import team.backend.goWithMe.global.common.AccessToken;
import team.backend.goWithMe.global.common.TokenProvider;
import team.backend.goWithMe.global.error.exception.ErrorCode;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PreferenceService {

    private final PreferenceRepository favoriteRepository;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    // create
    public PreferenceCreateResponse createFavorite(final Preference favorite) {

        Preference savedFavorite = favoriteRepository.save(favorite);

        return PreferenceCreateResponse.createResponse(savedFavorite);
    }

    // read
//    public FavoriteFindDTO findOne(final Long id) {
//        Favorite favorite = favoriteRepository.findById(id).orElseThrow(() -> {
//            throw new NotFountFavoriteException(ErrorCode.NOT_FOUND_FAVORITE);
//        });
//    }

    // update
    public void update(final Preference favorite, final Long id) {
        Preference findFavorite = favoriteRepository.findById(id).orElseThrow(() -> {
            throw new NotFountFavoriteException(ErrorCode.NOT_FOUND_FAVORITE);
        });

        findFavorite.updateFavorite(favorite);
    }
}
