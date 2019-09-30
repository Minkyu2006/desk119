package kr.co.broadwave.desk.record.file;

import kr.co.broadwave.desk.record.Record;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2019-09-16
 * Remark :
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="ar_record_file")
public class RecordUploadFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="af_id")
    private Long id;
    @ManyToOne(targetEntity = Record.class,fetch = FetchType.LAZY)
    @JoinColumn(name="ar_id")
    private Record record;
    @Column(name="af_filename")
    private String afFileName;
    @Column(name="af_comment")
    private String afComment;
    @Column(name="af_original_filename")
    private String afOriginalFilename;
    @Column(name="af_save_filename")
    private String afSaveFileName;
    @Column(name="af_file_path")
    private String afFilePath;
    @Column(name="af_content_type")
    private String contentType;
    @Column(name="af_size")
    private Long size;
    @Column(name="insert_date")
    private LocalDateTime insertDateTime;
}
