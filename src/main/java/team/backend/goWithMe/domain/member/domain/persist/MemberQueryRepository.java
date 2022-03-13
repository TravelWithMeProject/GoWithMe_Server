package team.backend.goWithMe.domain.member.domain.persist;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import team.backend.goWithMe.domain.preference.domain.persist.Preference;
import team.backend.goWithMe.domain.preference.domain.vo.Accommodation;
import team.backend.goWithMe.domain.preference.domain.vo.PreferenceArrival;
import team.backend.goWithMe.domain.preference.domain.vo.PreferencePeriod;

import java.util.List;

import static team.backend.goWithMe.domain.member.domain.persist.QMember.*;
import static team.backend.goWithMe.domain.preference.domain.persist.QPreference.*;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final JPAQueryFactory query;

    // 선호도 조사한 것을 기준으로 검색
    public List<Member> findAllByFavorite(final Preference memberPreference, final Pageable pageable) {
        return query.selectFrom(member)
                .leftJoin(member.preference)
                .where(
                        eqArrival(memberPreference.getPreferenceArrival())
                        .or(eqAccommodation(memberPreference.getAccommodation()))
                        .or(eqPeriod(memberPreference.getPreferencePeriod())))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults()
                .getResults();
    }

    private BooleanExpression eqPeriod(final PreferencePeriod preferencePeriod) {
        if (preferencePeriod == null) {
            return null;
        }

        return member.preference.preferencePeriod.eq(preferencePeriod);
    }

    private BooleanExpression eqAccommodation(final Accommodation accommodation) {
        if (accommodation == null) {
            return null;
        }

        return member.preference.accommodation.eq(accommodation);
    }

    private BooleanExpression eqArrival(final PreferenceArrival preferenceArrival) {
        if (preferenceArrival == null) {
            return null;
        }

        return member.preference.preferenceArrival.eq(preferenceArrival);
    }
}
