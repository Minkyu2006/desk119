package kr.co.broadwave.desk.notice.file;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author InSeok
 * Date : 2019-07-26
 * Remark :
 */
public interface UploadFileRepository extends JpaRepository<UploadFile,Long> {
    public UploadFile findOneByFileName(String fileName);
}
