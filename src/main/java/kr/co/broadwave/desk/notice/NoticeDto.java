package kr.co.broadwave.desk.notice;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author InSeok
 * Date : 2019-07-26
 * Remark :
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NoticeDto {
    private Long id;
    private String subject;
    private String content;
    private Integer hitCount;
    private LocalDateTime insertDateTime;
    private String insert_id;
    private String insert_name;
    private LocalDateTime modifyDateTime;
    private String modify_id;
    private String modify_name;

    // getter setter 에서 날짜 getter 만 손봄

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getHitCount() {
        return hitCount;
    }

    public void setHitCount(Integer hitCount) {
        this.hitCount = hitCount;
    }

    public String getInsertDateTime() {
        if (this.insertDateTime == null){
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return insertDateTime.format(dateTimeFormatter);


    }

    public void setInsertDateTime(LocalDateTime insertDateTime) {
        this.insertDateTime = insertDateTime;
    }

    public String getInsert_id() {
        return insert_id;
    }

    public void setInsert_id(String insert_id) {
        this.insert_id = insert_id;
    }

    public String getModifyDateTime() {
        if (this.modifyDateTime == null){
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return modifyDateTime.format(dateTimeFormatter);


    }

    public void setModifyDateTime(LocalDateTime modifyDateTime) {
        this.modifyDateTime = modifyDateTime;
    }

    public String getModify_id() {
        return modify_id;
    }

    public void setModify_id(String modify_id) {
        this.modify_id = modify_id;
    }

    public String getInsert_name() {
        return insert_name;
    }

    public void setInsert_name(String insert_name) {
        this.insert_name = insert_name;
    }

    public String getModify_name() {
        return modify_name;
    }

    public void setModify_name(String modify_name) {
        this.modify_name = modify_name;
    }
}
