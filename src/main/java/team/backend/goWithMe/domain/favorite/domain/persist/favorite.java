package team.backend.goWithMe.domain.favorite.domain.persist;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class favorite {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long id;

    @Column(name = "favorite_arrival", nullable = false, length = 50)
    private String favoriteArrival;

    @Column(nullable = false, length = 50)
    private String accommodation;

    @Column(name = "start_time", nullable = false)
    private LocalDate startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDate endTime;
}
