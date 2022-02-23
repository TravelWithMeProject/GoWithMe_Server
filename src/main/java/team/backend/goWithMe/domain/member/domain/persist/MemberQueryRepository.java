package team.backend.goWithMe.domain.member.domain.persist;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import team.backend.goWithMe.domain.preference.domain.persist.Preference;

import java.util.List;

import static team.backend.goWithMe.domain.member.domain.persist.QMember.*;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final JPAQueryFactory query;

    // 선호도 조사한 것을 기준으로 검색
    public List<Member> findAllByFavorite(final Preference memberFavorite, final Pageable pageable) {
        return query.selectFrom(member)
                .leftJoin(member.favorite)
                .where(member.favorite.favoriteArrival.eq(memberFavorite.getFavoriteArrival())
                        .or(member.favorite.accommodation.eq(memberFavorite.getAccommodation()))
                        .or(member.favorite.favoritePeriod.eq(memberFavorite.getFavoritePeriod())))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults()
                .getResults();
    }
}
