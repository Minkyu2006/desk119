package kr.co.broadwave.desk.record;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
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

                        qRecord.modifyDateTime

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

        query.orderBy(qRecord.modifyDateTime.desc());

        final List<RecrodListDto> records = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(records, pageable, query.fetchCount());

    }

//    @Override
//    public Optional<RecordViewDto> findByViewId(Long id) {
//        QRecord qRecord = QRecord.record;
//
//        JPQLQuery<RecordViewDto> query = from(qRecord)
//                .select(Projections.constructor(RecordViewDto.class,
//                        qRecord.id,
//                        qRecord.arNumber,
//                        qRecord.arTitle,
//                        qRecord.arWriter,
//                        qRecord.arRelatedId,
//                        qRecord.arRelatedDetail,
//                        qRecord.arPapers,
//                        qRecord.arIntoStart,
//                        qRecord.arIntoEnd,
//                        qRecord.arLocationCityType,
//                        qRecord.arLocationAddressType,
//                        qRecord.arLocationDetail,
//                        qRecord.argita,
//                        qRecord.arPurpose,
//                        qRecord.arResponsibil,
//                        qRecord.arEngine,
//                        qRecord.arOutline,
//                        qRecord.arResult,
//                        qRecord.arOpinion,
//                        qRecord.arDisasterItem,
//                        qRecord.arFacItem
//                ));
//
//        return null;
//
//    }

}
