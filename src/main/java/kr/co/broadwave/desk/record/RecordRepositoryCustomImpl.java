package kr.co.broadwave.desk.record;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.broadwave.desk.mastercode.QMasterCode;
import kr.co.broadwave.desk.record.responsibil.QResponsibil;
import kr.co.broadwave.desk.record.responsibil.ResponsibilListDto;
import kr.co.broadwave.desk.teams.QTeam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * @author Minkyu
 * Date : 2019-09-11
 * Remark :
 */
@Slf4j
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

        final List<RecrodListDto> records = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query).fetch();
        return new PageImpl<>(records, pageable, query.fetchCount());

    }

    @Override
    public List<RecordViewPrintDto> findByIdViewList(List<Long> ids) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QRecord record = QRecord.record;
        QMasterCode masterCode = QMasterCode.masterCode;

        return queryFactory.select(Projections.constructor(RecordViewPrintDto.class,
                record.id,record.arNumber,record.arTitle,record.arWriter,
                masterCode.name,record.arRelatedDetail,
                record.arPapers,record.arIntoStart,record.arIntoEnd,
                record.arLocationCityType,record.arLocationAddressType,
                record.arLocationDetail,record.arPurpose,record.arEngine,
                record.arOutline,record.arResult,record.arOpinion,
                record.arDisasterItem,record.arFacItem,
                record.argita,record.arDisasterGita))
                .from(record)
                .where(record.id.in(ids))
                .innerJoin(record.arRelatedId,masterCode)
//                .orderBy(record.id.asc())
                .fetch();
    }

    @Override
    public  List<ResponsibilListDto> recordResponList(List<Long> recordList) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QResponsibil responsibil = QResponsibil.responsibil;
        QTeam team = QTeam.team;

        return queryFactory.select(Projections.constructor(ResponsibilListDto.class,
                responsibil.record.id,responsibil.arEmployeeNumber,responsibil.arEmployeeName,team.teamname))
                .from(responsibil)
                .where(responsibil.record.id.in(recordList))
                .innerJoin(responsibil.team,team)
                .fetch();
    }

    @Override
    public Page<RecrodStatisticDto> findByStatisticList(String typeName,Pageable pageable,String num) {
        QRecord qRecord = QRecord.record;

        JPQLQuery<RecrodStatisticDto> query = from(qRecord)
                .select(Projections.constructor(RecrodStatisticDto.class,
                        qRecord.id,
                        qRecord.arDisasterType,
                        qRecord.arFac,
                        qRecord.arDisasterItem,
                        qRecord.arFacItem,
                        qRecord.arLocationCityType,
                        qRecord.arRelatedId,
                        qRecord.arIntoStart,
                        qRecord.arIntoEnd,
                        qRecord.modify_id,
                        qRecord.arRecordState
                ));

        query.where(qRecord.arRecordState.eq(1));

        if(num.equals("1")){
            query.where(qRecord.arDisasterItem.contains(typeName));
        }else{
            switch (typeName) {
                case "옹벽":
                    query.where(qRecord.arFacItem.contains(typeName).or(qRecord.arFacItem.contains("비탈면")));
                    break;
                case "도로":
                    query.where(qRecord.arFacItem.contains(typeName).or(qRecord.arFacItem.contains("도로시설물")));
                    break;
                case "지반":
                    query.where(qRecord.arFacItem.contains(typeName).or(qRecord.arFacItem.contains("지하시설물")));
                    break;
                default:
                    query.where(qRecord.arFacItem.contains(typeName));
                    break;
            }
        }

        query.orderBy(qRecord.arNumber.desc());

        final List<RecrodStatisticDto> records = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query).fetch();
        return new PageImpl<>(records, pageable, query.fetchCount());

    }

    @Override
    public List<RecrodStatisticDto> findByStatisticList2(String typeName,String num) {
        QRecord qRecord = QRecord.record;

        JPQLQuery<RecrodStatisticDto> query = from(qRecord)
                .select(Projections.constructor(RecrodStatisticDto.class,
                        qRecord.id,
                        qRecord.arDisasterType,
                        qRecord.arFac,
                        qRecord.arDisasterItem,
                        qRecord.arFacItem,
                        qRecord.arLocationCityType,
                        qRecord.arRelatedId,
                        qRecord.arIntoStart,
                        qRecord.arIntoEnd,
                        qRecord.modify_id,
                        qRecord.arRecordState
                ));

        query.where(qRecord.arRecordState.eq(1));

        if(num.equals("1")){
            query.where(qRecord.arDisasterItem.contains(typeName));
        }else{
            switch (typeName) {
                case "옹벽":
                    query.where(qRecord.arFacItem.contains(typeName).or(qRecord.arFacItem.contains("비탈면")));
                    break;
                case "도로":
                    query.where(qRecord.arFacItem.contains(typeName).or(qRecord.arFacItem.contains("도로시설물")));
                    break;
                case "지반":
                    query.where(qRecord.arFacItem.contains(typeName).or(qRecord.arFacItem.contains("지하시설물")));
                    break;
                default:
                    query.where(qRecord.arFacItem.contains(typeName));
                    break;
            }
        }

        query.orderBy(qRecord.arNumber.desc());

        return query.fetch();

    }

}
