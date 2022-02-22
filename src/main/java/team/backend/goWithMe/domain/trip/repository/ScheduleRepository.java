package team.backend.goWithMe.domain.trip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import team.backend.goWithMe.domain.trip.domain.persist.Schedule;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s WHERE s.timeTable.id =:timeTableId")
    @Transactional(readOnly = true)
    List<Schedule> findByTimeTableId(@Param("timeTableId") Long timeTableId);

    @Query("SELECT s FROM Schedule s WHERE s.timeTable.id =:timeTableId order by s.schedulePeriod.detailStart asc")
    @Transactional(readOnly = true)
    List<Schedule> findByTimeTableIdOrderByPeriodStart(@Param("timeTableId") Long timeTableId);
}