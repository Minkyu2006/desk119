package kr.co.broadwave.desk.mastercode;

import kr.co.broadwave.desk.bscodes.CodeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author InSeok
 * Date : 2019-08-02
 * Remark :
 */
@Service
public class MasterCodeService {

    private final MasterCodeRepository masterCodeRepository;
    private final MasterCodeCustom masterCodeCustom;

    @Autowired
    public MasterCodeService(MasterCodeRepository masterCodeRepository, MasterCodeCustom masterCodeCustom) {
        this.masterCodeRepository = masterCodeRepository;
        this.masterCodeCustom = masterCodeCustom;
    }


    //마스터코드저장
    public MasterCode save(MasterCode masterCode){
        return masterCodeRepository.save(masterCode);
    }

    //코드리스트조회
    public Page<MasterCodeDto> findAllBySearchStrings(CodeType codeType, String code, String name, Pageable pageable){
        return masterCodeCustom.findAllBySearchStrings(codeType,code,name,pageable);
    }


}
