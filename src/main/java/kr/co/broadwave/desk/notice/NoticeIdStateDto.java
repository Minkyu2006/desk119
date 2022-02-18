package kr.co.broadwave.desk.notice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Minkyu
 * Date : 2020-0617
 * Remark :
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NoticeIdStateDto {
    private Long id;
    private Integer bnState;

    public Long getId() {
        return id;
    }

    public Integer getBnState() {
        return bnState;
    }
}
