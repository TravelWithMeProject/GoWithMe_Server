package team.backend.goWithMe.domain.preference.domain.persist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {

}
