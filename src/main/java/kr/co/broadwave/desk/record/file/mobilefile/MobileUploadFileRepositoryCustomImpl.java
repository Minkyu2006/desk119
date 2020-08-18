package kr.co.broadwave.desk.record.file.mobilefile;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.broadwave.desk.accounts.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author Minkyu
 * Date : 2020-08-06
 * Remark :
 */
@Repository
public class MobileUploadFileRepositoryCustomImpl extends QuerydslRepositorySupport implements MobileUploadFileRepositoryCustom {

    public MobileUploadFileRepositoryCustomImpl() {
        super(MobileUploadFile.class);
    }

    @Override
    public List<MobileUploadFileDto> findByMobileUploadFileList(Account account) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        QMobileUploadFile mobileUploadFile = QMobileUploadFile.mobileUploadFile;

        return queryFactory.select(Projections.constructor(MobileUploadFileDto.class,
                mobileUploadFile.id,
                mobileUploadFile.afmSaveFilename,
                mobileUploadFile.afmSaveFilename,
                mobileUploadFile.afmComment,
                mobileUploadFile.insertDateTime,
                mobileUploadFile.insertDateTime))
                .from(mobileUploadFile)
                .orderBy(mobileUploadFile.id.desc())
                .where(mobileUploadFile.account.eq(account))
                .fetch();
    }

    @Override
    public Page<MobileUploadFileDto> findByMobileUploadFilePage(Account account, LocalDateTime s_from, LocalDateTime s_to, Pageable pageable){

        QMobileUploadFile mobileUploadFile = QMobileUploadFile.mobileUploadFile;

        JPQLQuery<MobileUploadFileDto> query = from(mobileUploadFile)
                .select(Projections.constructor(MobileUploadFileDto.class,
                        mobileUploadFile.id,
                        mobileUploadFile.afmSaveFilename,
                        mobileUploadFile.afmSaveFilename,
                        mobileUploadFile.afmComment,
                        mobileUploadFile.insertDateTime,
                        mobileUploadFile.insertDateTime
                ));

        query.where(mobileUploadFile.account.eq(account));

        if(s_from != null && s_to != null){
            query.where(mobileUploadFile.insertDateTime.between(s_from,s_to));
        }else if(s_from != null){
            query.where(mobileUploadFile.insertDateTime.goe(s_from));
        }else if(s_to != null){
            query.where(mobileUploadFile.insertDateTime.loe(s_to));
        }

        query.orderBy(mobileUploadFile.id.desc());

        final List<MobileUploadFileDto> mobileUploadFileDtos = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query).fetch();
        return new PageImpl<>(mobileUploadFileDtos, pageable, query.fetchCount());

    }

}
