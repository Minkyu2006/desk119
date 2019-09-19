package kr.co.broadwave.desk.record.file;

import kr.co.broadwave.desk.record.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Minkyu
 * Date : 2019-09-16
 * Remark :
 */
public interface RecordUploadFileRepository extends JpaRepository<RecordUploadFile,Long> {
    List<RecordUploadFile> findByRecord(Record record);
}