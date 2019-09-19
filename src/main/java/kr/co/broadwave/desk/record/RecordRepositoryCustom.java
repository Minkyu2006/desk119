package kr.co.broadwave.desk.record;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * @author Minkyu
 * Date : 2019-09-11
 * Remark : recordRepositoryCustom 리스트조회
 */
public interface RecordRepositoryCustom {
    Page<RecrodListDto> findAllBySearchStrings(String arNumber, String arTitle, String arWriter, Pageable pageable);
}
