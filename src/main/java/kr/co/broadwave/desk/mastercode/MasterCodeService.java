package kr.co.broadwave.desk.mastercode;

import kr.co.broadwave.desk.bscodes.CodeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author InSeok
 * Date : 2019-08-02
 * Remark :
 */
@Service
public class MasterCodeService {

    private final MasterCodeRepository masterCodeRepository;
    private final MasterCodeRepositoryCustom masterCodeRepositoryCustom;

    @Autowired
    public MasterCodeService(MasterCodeRepository masterCodeRepository, MasterCodeRepositoryCustom masterCodeRepositoryCustom) {
        this.masterCodeRepository = masterCodeRepository;
        this.masterCodeRepositoryCustom = masterCodeRepositoryCustom;
    }


    //마스터코드저장
    public MasterCode save(MasterCode masterCode)
    {
        return masterCodeRepository.save(masterCode);
    }

    //코드리스트조회
    public Page<MasterCodeDto> findAllBySearchStrings(CodeType codeType, String code, String name, Pageable pageable){
        return masterCodeRepositoryCustom.findAllBySearchStrings(codeType,code,name,pageable);
    }

    //코드분류류와 코드 겁색조건으로 코드값 개별조회
    public Optional<MasterCode> findByCoAndCodeTypeAndCode(CodeType codeType,String code){
        return masterCodeRepository.findByAndCodeTypeAndCode(codeType,code);
    }
    public Optional<MasterCode> findById(Long id){
        return masterCodeRepository.findById(id);

    }


    public void delete(MasterCode masterCode) {
        masterCodeRepository.delete(masterCode);
    }
}
