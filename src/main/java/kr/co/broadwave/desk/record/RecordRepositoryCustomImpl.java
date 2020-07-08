package kr.co.broadwave.desk.record;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.broadwave.desk.record.file.QRecordUploadFile;
import kr.co.broadwave.desk.record.file.RecordUploadFile;
import kr.co.broadwave.desk.record.file.RecordUploadFileDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Minkyu
 * Date : 2019-09-11
 * Remark :
 */
@Repository
public class RecordRepositoryCustomImpl extends QuerydslRepositorySupport implements RecordRepositoryCustom{

    public RecordRepositoryCustomImpl() {
        super(Record.class);
    }

    @Override
    public Page<RecrodListDto> findAllBySearchStrings(String arNumber, String arTitle, String arWriter, Pageable pageable) {
        QRecord qRecord = QRecord.record;

        JPQLQuery<RecrodListDto> query = from(qRecord)
                .select(Projections.constructor(RecrodListDto.class,
                        qRecord.id,
                        qRecord.arNumber,
                        qRecord.arTitle,
                        qRecord.arWriter,
                        qRecord.arDisasterItem,
                        qRecord.arFacItem,
                        qRecord.arIntoStart,
                        qRecord.arIntoEnd,
                        qRecord.arLocationCityType,
                        qRecord.arLocationAddressType,
                        qRecord.modifyDateTime,
                        qRecord.insert_id,
                        qRecord.arRecordState
                ));

        if (arNumber != null && !arNumber.isEmpty()){
            query.where(qRecord.arNumber.likeIgnoreCase(arNumber.concat("%")));
        }

        if (arTitle != null && !arTitle.isEmpty()){
            query.where(qRecord.arTitle.containsIgnoreCase(arTitle));
        }

        if (arWriter != null && !arWriter.isEmpty()){
            query.where(qRecord.arWriter.containsIgnoreCase(arWriter));
        }

        query.orderBy(qRecord.arNumber.desc());

        final List<RecrodListDto> records = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(records, pageable, query.fetchCount());

    }

//    @Override
//    public List<CollectionInfoDto> findByCollectionInfoQueryDsl(String ctCode) {
//
//        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
//
//        QCollectionTask collectionTask = QCollectionTask.collectionTask;
//        QEquipment equipment =QEquipment.equipment;
//        QIModel iModel =QIModel.iModel;
//
//        return queryFactory.select(Projections.constructor(CollectionInfoDto.class,
//                collectionTask.ctCode,collectionTask.ctSeq,collectionTask.yyyymmdd,
//                collectionTask.deviceid,collectionTask.accountId.username,collectionTask.accountId.userid,
//                collectionTask.vehicleId.vcNumber,collectionTask.vehicleId.vcName,iModel.mdType.name))
//                .from(collectionTask)
//                .where(collectionTask.ctCode.eq(ctCode))
//                .groupBy(collectionTask.ctSeq)
//                .innerJoin(collectionTask.emId,equipment)
//                .innerJoin(equipment.mdId,iModel)
//                .fetch();
//    }

}
