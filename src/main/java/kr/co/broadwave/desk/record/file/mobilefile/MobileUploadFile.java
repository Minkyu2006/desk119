package kr.co.broadwave.desk.record.file.mobilefile;

import kr.co.broadwave.desk.accounts.Account;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2020-08-05
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
@Table(name="ar_record_mobile_file")
public class MobileUploadFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="af_m_id")
    private Long id;
    @ManyToOne(targetEntity = Account.class,fetch = FetchType.LAZY)
    @JoinColumn(name="account_id")
    private Account account;
    @Column(name="af_m_filename")
    private String afmFilename;
    @Column(name="af_m_comment")
    private String afmComment;
    @Column(name="af_m_original_filename")
    private String afmOriginalFilename;
    @Column(name="af_m_save_filename")
    private String afmSaveFilename;
    @Column(name="af_m_file_path")
    private String afmFilePath;
    @Column(name="af_m_content_type")
    private String afmContentType;
    @Column(name="af_m_size")
    private Long afmSize;
    @Column(name="insert_data")
    private LocalDateTime insertDateTime;
}
