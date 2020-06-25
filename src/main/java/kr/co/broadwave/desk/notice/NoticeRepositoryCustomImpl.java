package kr.co.broadwave.desk.notice;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.broadwave.desk.accounts.QAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author InSeok
 * Date : 2019-07-26
 * Remark :
 */
@Repository
public class NoticeRepositoryCustomImpl extends QuerydslRepositorySupport implements NoticeRepositoryCustom {

    public NoticeRepositoryCustomImpl() {
        super(Notice.class);
    }



    @Override
    public Page<NoticeDto> findAllBySearchStrings(String subject, String username,  Pageable pageable) {
        QNotice qNotice = QNotice.notice;

        JPQLQuery<NoticeDto> query = from(qNotice)
                .select(Projections.constructor(NoticeDto.class,
                        qNotice.id,
                        qNotice.subject,
                        qNotice.content,
                        qNotice.hitCount,
                        qNotice.bnState,
                        qNotice.insertDateTime,
                        qNotice.insert_id,
                        qNotice.insert_name,
                        qNotice.modifyDateTime,
                        qNotice.modify_id,
                        qNotice.modify_name
                ));
        if (username != null && !username.isEmpty()){
            query.where(qNotice.modify_name.containsIgnoreCase(username));
        }

        if (subject != null && !subject.isEmpty()){
            query.where(qNotice.subject.containsIgnoreCase(subject));
        }

        query.orderBy(qNotice.id.desc());

        final List<NoticeDto> notices = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(notices, pageable, query.fetchCount());

    }

    @Override
    @Transactional
    public Long hitCountUpdate(Notice notice) {
        QNotice qNotice = QNotice.notice;
        Integer hitCount = notice.getHitCount() + 1 ;

        return update(qNotice).where(qNotice.id.eq(notice.getId()))
                .set(qNotice.hitCount,hitCount)
                .execute();

    }

    @Override
    public List<NoticeIdStateDto> findByIdState() {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QNotice notice = QNotice.notice;

        return queryFactory.select(Projections.constructor(NoticeIdStateDto.class,
                notice.id,notice.bnState))
                .from(notice)
                .fetch();
    }
}
