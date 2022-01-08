package team.backend.goWithMe.domain.info.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

import static lombok.AccessLevel.*;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
public class ConfirmCount {

    @NotEmpty
    @Column(name = "confirm_count")
    private Long confirmCount;

    // 값이 음수, null => 0으로 설정
    public static ConfirmCount of(Long confirmCount){
        if(confirmCount.equals(null) || confirmCount.longValue() < 0) {
            return new ConfirmCount(0L);
        }
        return new ConfirmCount(confirmCount);
    }
    
}
