package kr.co.broadwave.desk.record;

import kr.co.broadwave.desk.record.responsibil.ResponsibilListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Minkyu
 * Date : 2019-09-11
 * Remark : recordRepositoryCustom 리스트조회
 */
public interface RecordRepositoryCustom {

    Page<RecrodListDto> findAllBySearchStrings(String arNumber, String arTitle, String arWriter, Pageable pageable);

    List<RecordViewPrintDto> findByIdViewList(List<Long> ids);

    List<ResponsibilListDto> recordResponList(List<Long> recordList);

    Page<RecrodStatisticDto> findByStatisticList(String typeName,Pageable pageable,String num);
    List<RecrodStatisticDto> findByStatisticList2(String typeName,String num);
}
