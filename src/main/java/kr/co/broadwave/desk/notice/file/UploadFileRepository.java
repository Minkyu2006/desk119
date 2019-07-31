package kr.co.broadwave.desk.notice.file;

import kr.co.broadwave.desk.notice.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author InSeok
 * Date : 2019-07-26
 * Remark :
 */
public interface UploadFileRepository extends JpaRepository<UploadFile,Long> {
    List<UploadFile> findByNotice(Notice notice);

}
