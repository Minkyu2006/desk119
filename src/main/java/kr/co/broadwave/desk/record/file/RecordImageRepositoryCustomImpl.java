package kr.co.broadwave.desk.record.file;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.broadwave.desk.record.Record;
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
public class RecordImageRepositoryCustomImpl extends QuerydslRepositorySupport implements RecordImageRepositoryCustom {

    public RecordImageRepositoryCustomImpl() {
        super(Record.class);
    }

    @Override
    public RecordUploadFileDto recordUploadFile(Long record, int stateVal){

        QRecordUploadFile recordUploadFile = QRecordUploadFile.recordUploadFile;

        JPQLQuery<RecordUploadFileDto> query = from(recordUploadFile)
                .select(Projections.constructor(RecordUploadFileDto.class,
                        recordUploadFile.id,
                        recordUploadFile.record.id,
                        recordUploadFile.afComment,
                        recordUploadFile.afSaveFileName,
                        recordUploadFile.afFileName,
                        recordUploadFile.afState
                ));

        if (record != null){
            query.where(recordUploadFile.record.id.eq(record));
        }

        if (stateVal != 0){
            query.where(recordUploadFile.afState.eq(stateVal));
        }

        return query.fetchOne();
    }

    @Override
    public List<RecordUploadFileDto> recordUploadFileList(Long record, int stateVal) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        QRecordUploadFile recordUploadFile = QRecordUploadFile.recordUploadFile;

        return queryFactory.select(Projections.constructor(RecordUploadFileDto.class,
                recordUploadFile.id,
                recordUploadFile.record.id,
                recordUploadFile.afComment,
                recordUploadFile.afSaveFileName,
                recordUploadFile.afFileName,
                recordUploadFile.afState))
                .from(recordUploadFile)
                .where(recordUploadFile.record.id.eq(record))
                .where(recordUploadFile.afState.eq(stateVal))
                .fetch();
    }

    @Override
    @Transactional
    public long fileDel(Record record) {
        QRecordUploadFile recordUploadFile = QRecordUploadFile.recordUploadFile;
        return delete(recordUploadFile).where(recordUploadFile.record.eq(record)).execute();
    }



    @Override
    public List<RecordUploadFileDto> recordUploadFilePrint(List<Long> recordList, int stateVal) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        QRecordUploadFile recordUploadFile = QRecordUploadFile.recordUploadFile;

        return queryFactory.select(Projections.constructor(RecordUploadFileDto.class,
                recordUploadFile.id,
                recordUploadFile.record.id,
                recordUploadFile.afComment,
                recordUploadFile.afSaveFileName,
                recordUploadFile.afFileName,
                recordUploadFile.afState))
                .from(recordUploadFile)
                .where(recordUploadFile.record.id.in(recordList))
                .where(recordUploadFile.afState.eq(stateVal))
                .fetch();
    }

}
