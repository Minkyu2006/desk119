package kr.co.broadwave.desk.record.file;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Minkyu
 * Date : 2019-09-10
 * Time : 10:11
 * Remark : RecordDto
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
public class RecordUploadFileDto {
    private Long id;
    private Long recordId;
    private String afComment;
    private String afSaveFileName;
    private String afFileName;
    private Integer afState;

    public Long getRecordId() {
        return recordId;
    }

    public Long getId() {
        return id;
    }

    public String getAfFileName() {
        return afFileName;
    }

    public Integer getAfState() {
        return afState;
    }

    public String getAfComment() {
        return afComment;
    }

    public String getAfSaveFileName() {
        return afSaveFileName;
    }
}
