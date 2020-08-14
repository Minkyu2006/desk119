package kr.co.broadwave.desk.record.file.mobilefile;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Minkyu
 * Date : 2020-08-05
 * Time :
 * Remark : MobileUploadFileDto
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
public class MobileUploadFileDto {
    private Long id;
    private String afmSaveFilename;
    private String afmComment;
    private LocalDateTime insertDateTime;

    public String getInsertDateTime() {
        return insertDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH시 mm분"));
    }

    public String getAfmComment() {
        return afmComment;
    }

    public Long getId() {
        return id;
    }

    public String getAfmSaveFilename() {
        return afmSaveFilename;
    }
}
