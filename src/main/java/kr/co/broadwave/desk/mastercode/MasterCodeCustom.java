package kr.co.broadwave.desk.mastercode;

import kr.co.broadwave.desk.bscodes.CodeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author InSeok
 * Date : 2019-08-02
 * Remark :
 */
public interface MasterCodeCustom {
    Page<MasterCodeDto> findAllBySearchStrings(CodeType codeType, String code,String name, Pageable pageable);

}
