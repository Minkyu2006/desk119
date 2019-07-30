package kr.co.broadwave.desk.notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author InSeok
 * Date : 2019-07-26
 * Remark :
 */
public interface NoticeRepositoryCustom {
    Page<NoticeDto> findAllByApproval(String username, String subject, Pageable pageable);
    Page<NoticeDto> findAllBySearchStrings(String subject,String username, Pageable pageable);
}
