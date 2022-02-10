package team.backend.goWithMe.domain.favorite.domain.persist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

}
