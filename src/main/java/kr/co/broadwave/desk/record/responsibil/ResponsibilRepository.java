package kr.co.broadwave.desk.record.responsibil;

import kr.co.broadwave.desk.record.Record;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author Minkyu
 * Date : 2019-09-19
 * Remark :
 */
public interface ResponsibilRepository extends JpaRepository<Responsibil,Long> {

    //@EntityGraph(attributePaths = {"team"})
    @Query("select a from Responsibil a join fetch a.team where a.record = :record")
    List<Responsibil> findByRecord(Record record);

    Optional<Responsibil> findById(Long id);
}