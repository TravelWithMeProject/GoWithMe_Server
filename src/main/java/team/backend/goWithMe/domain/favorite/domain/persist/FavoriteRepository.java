package team.backend.goWithMe.domain.favorite.domain.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import team.backend.goWithMe.domain.favorite.domain.vo.FavoriteArrival;

public interface FavoriteRepository extends JpaRepository<FavoriteArrival, Long> {

}
