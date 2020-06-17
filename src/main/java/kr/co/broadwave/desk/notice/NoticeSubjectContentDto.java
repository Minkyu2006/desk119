package kr.co.broadwave.desk.notice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Minkyu
 * Date : 2020-06-17
 * Remark :
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NoticeSubjectContentDto {
    private String subject;
    private String content;

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }
}
