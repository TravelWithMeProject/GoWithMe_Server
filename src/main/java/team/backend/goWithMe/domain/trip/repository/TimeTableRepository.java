package team.backend.goWithMe.domain.trip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import team.backend.goWithMe.domain.member.domain.persist.Member;
import team.backend.goWithMe.domain.trip.domain.persist.TimeTable;

import java.util.List;

public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {

    @Query("SELECT t FROM TimeTable t WHERE t.member.id =:userId")
    @Transactional(readOnly = true)
    List<TimeTable> findByUserId(@Param("userId")Long userId);
}