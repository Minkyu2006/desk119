package kr.co.broadwave.desk.notice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * @author InSeok
 * Date : 2019-07-26
 * Remark :
 */
public interface NoticeRepository extends JpaRepository<Notice,Long>, QuerydslPredicateExecutor<Notice> {

}
