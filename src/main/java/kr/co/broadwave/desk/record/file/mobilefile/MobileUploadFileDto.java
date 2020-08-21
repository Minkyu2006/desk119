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
    private String accountName;
    private String afmSaveFilename;
    private String afmSaveFileThumname;
    private String afmComment;
    private LocalDateTime insertDateTime;
    private LocalDateTime insertDate;

    public String getAccountName() {
        return accountName;
    }

    public String getInsertDateTime() {
        return insertDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH시 mm분"));
    }

    public String getAfmSaveFileThumname() {
        return afmSaveFileThumname.substring(0,11)+"s_"+afmSaveFileThumname.substring(11);
    }

    public String getInsertDate() {
        return insertDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
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
