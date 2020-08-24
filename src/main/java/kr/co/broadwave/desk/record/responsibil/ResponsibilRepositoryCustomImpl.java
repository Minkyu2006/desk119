package kr.co.broadwave.desk.record.responsibil;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.broadwave.desk.record.Record;
import kr.co.broadwave.desk.record.file.QRecordUploadFile;
import kr.co.broadwave.desk.record.file.RecordImageRepositoryCustom;
import kr.co.broadwave.desk.record.file.RecordUploadFileDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Minkyu
 * Date : 2020-07-08
 * Remark :
 */
@Repository
public class ResponsibilRepositoryCustomImpl extends QuerydslRepositorySupport implements ResponsibilRepositoryCustom {

    public ResponsibilRepositoryCustomImpl() {
        super(Responsibil.class);
    }

    @Override
    @Transactional
    public long resDel(Record record){
        QResponsibil responsibil = QResponsibil.responsibil;
        return delete(responsibil).where(responsibil.record.eq(record)).execute();
    }

}
