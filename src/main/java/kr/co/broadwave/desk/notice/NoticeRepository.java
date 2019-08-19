package kr.co.broadwave.desk.notice;

import com.querydsl.core.types.Predicate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

/**
 * @author InSeok
 * Date : 2019-07-26
 * Remark :
 */
public interface NoticeRepository extends JpaRepository<Notice,Long>, QuerydslPredicateExecutor<Notice> {

    List<Notice> findTop1ByOrderByIdDesc();

}
