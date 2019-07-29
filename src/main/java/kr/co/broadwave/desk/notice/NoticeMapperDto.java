package kr.co.broadwave.desk.notice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author InSeok
 * Date : 2019-07-29
 * Remark :
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeMapperDto {
    private Long id;
    private String subject;
    private String content;
}
