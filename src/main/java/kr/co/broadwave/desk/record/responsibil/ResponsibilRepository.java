package kr.co.broadwave.desk.record.responsibil;

import kr.co.broadwave.desk.record.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Minkyu
 * Date : 2019-09-19
 * Remark :
 */
public interface ResponsibilRepository extends JpaRepository<Responsibil,Long> {

    List<Responsibil> findByRecord(Record record);
}