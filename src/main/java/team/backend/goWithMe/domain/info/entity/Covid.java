package team.backend.goWithMe.domain.info.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.domain.info.converter.BooleanToYNConverter;
import team.backend.goWithMe.domain.info.enums.DistanceStep;
import team.backend.goWithMe.domain.info.vo.ConfirmCount;

import javax.persistence.*;

import static javax.persistence.EnumType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@Table(name = "covid")
@NoArgsConstructor(access = PROTECTED)
public class Covid extends Info{

    @Embedded
    private ConfirmCount confirmCount;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "status", length = 1)
    private Boolean status;

    @Enumerated(STRING)
    @Column(name = "distance_step")
    private DistanceStep distanceStep;
}
