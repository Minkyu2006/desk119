package kr.co.broadwave.desk.notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author InSeok
 * Date : 2019-07-26
 * Remark :
 */
public interface NoticeRepositoryCustom {

    Page<NoticeDto> findAllBySearchStrings(String subject,String username, Pageable pageable);
    Long hitCountUpdate(Notice notice);

    List<NoticeIdStateDto> findByIdState();
}
