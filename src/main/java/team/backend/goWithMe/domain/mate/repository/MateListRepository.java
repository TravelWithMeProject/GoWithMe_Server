package team.backend.goWithMe.domain.mate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.backend.goWithMe.domain.mate.domain.persist.MateList;

import javax.persistence.EntityManager;

public interface MateListRepository extends JpaRepository<MateList, Long> {
}
