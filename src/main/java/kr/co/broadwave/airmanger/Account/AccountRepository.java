package kr.co.broadwave.airmanger.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author Minkyu
 * Date : 2022-02-18
 * Time :
 * Remark : 사용자정보 레파지토리
 */
public interface AccountRepository extends JpaRepository<Account,Long>,QuerydslPredicateExecutor<Account> {

    @Query("select a from Account a where a.userId = :userId")
    Optional<Account> findByUserid(@Param("userId") String userId);

}
