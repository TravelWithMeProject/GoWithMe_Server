package team.backend.goWithMe.domain.info.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.global.common.BaseTimeEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;
import static javax.persistence.InheritanceType.*;
import static lombok.AccessLevel.*;

@Getter
@Entity
@Inheritance(strategy = JOINED)
@DiscriminatorColumn(name = "dtype")
@NoArgsConstructor(access = PROTECTED)
public class Info extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "info_id")
    private Long id;

    // enum으로 몇개의 출처들을 정해서 받아오면 좋을 듯합니다.
    @Column(name = "source", length = 50)
    private String source;

    // 정보에 해당하는 위치
    
}
