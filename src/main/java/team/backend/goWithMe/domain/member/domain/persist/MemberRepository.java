package team.backend.goWithMe.domain.member.domain.persist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import team.backend.goWithMe.domain.member.domain.vo.UserEmail;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(final UserEmail email);
    boolean existsByEmail(final UserEmail email);
    void deleteByEmail(final UserEmail email);
    Page<Member> findAll(final Pageable pageable);
}
