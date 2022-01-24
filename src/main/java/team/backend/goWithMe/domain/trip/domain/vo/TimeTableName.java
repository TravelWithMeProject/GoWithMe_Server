package team.backend.goWithMe.domain.trip.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.domain.trip.error.exception.TimeTableNameInvalidException;
import team.backend.goWithMe.global.error.exception.ErrorCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class TimeTableName {

    @NotBlank
    @Column(name = "table_name", length = 50) // 한글 기준 25자 제한
    private String tableName;

    public static TimeTableName from(String tableName) {
        validateTimeTableName(tableName);
        return new TimeTableName(tableName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeTableName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof TimeTableName))
            return false;
        TimeTableName name = (TimeTableName)o;
        return Objects.equals(timeTableName(), name.tableName);
    }

    @JsonValue
    public String timeTableName() {
        return this.tableName;
    }

    private static void validateTimeTableName(String tableName) {
        if (tableName == null || tableName.isBlank()) {
            throw new TimeTableNameInvalidException("시간표 명은 필수입니다.", ErrorCode.INVALID_INPUT_VALUE);
        }
    }
}
