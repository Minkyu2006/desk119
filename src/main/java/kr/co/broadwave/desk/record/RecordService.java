package kr.co.broadwave.desk.record;

import kr.co.broadwave.desk.keygenerate.KeyGenerateService;

//import kr.co.broadwave.desk.record.responsibil.Responsibil;
//import kr.co.broadwave.desk.record.responsibil.ResponsibilRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author MinKyu
 * Date : 2019-09-06
 * Remark : 출동일지 레코드서비스
 */
@Slf4j
@Service
public class RecordService {

    private final RecordRepository recordRepository;
    private final ModelMapper modelMapper;
    private final RecordRepositoryCustom recordRepositoryCustom;
    private final KeyGenerateService keyGenerateService;
//    private final ResponsibilRepository responsibilRepository;
    @Autowired
    public RecordService(RecordRepository recordRepository,
                         RecordRepositoryCustom recordRepositoryCustom,
                         KeyGenerateService keyGenerateService,
//                         ResponsibilRepository responsibilRepository,
                         ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.recordRepository = recordRepository;
        this.recordRepositoryCustom = recordRepositoryCustom;
        this.keyGenerateService = keyGenerateService;
//        this.responsibilRepository = responsibilRepository;
    }

    // 출동일지 고유번호 저장 및 입력데이터저장
    public Record save(Record record){
        if ( record.getArNumber() == null || record.getArNumber().isEmpty()){
            log.info("신규 RC코드생성");
            Date now = new Date();
            SimpleDateFormat yyMM = new SimpleDateFormat("yyMM");
            String arNumber = keyGenerateService.keyGenerate("ar_record", yyMM.format(now), record.getModify_id());
            record.setArNumber(arNumber);
        }
        return recordRepository.save(record);
    }

    public Optional<Record> findByIdRecord(Long id){
        return  recordRepository.findById(id);
    }

    public RecordDto findById(Long id){
        Optional<Record> optionalRecord = recordRepository.findById(id);
        if (optionalRecord.isPresent()) {
            return modelMapper.map(optionalRecord.get(), RecordDto.class);
        } else {
            return null;
        }
    }

    public RecordViewDto findByIdView(Long id){
        Optional<Record> optionalRecord = recordRepository.findById(id);
        if (optionalRecord.isPresent()) {
            return modelMapper.map(optionalRecord.get(), RecordViewDto.class);
        } else {
            return null;
        }
    }

    public Page<RecrodListDto> findAllBySearchStrings(String arNumber, String arTitle, String arWriter, Pageable pageable){
        return recordRepositoryCustom.findAllBySearchStrings(arNumber,arTitle,arWriter,pageable);

    }

    public void delete(Record record){
        recordRepository.delete(record);
    }

//    //조사담당자
////    public List<Responsibil> recordRespon(Long record_id){
////        log.info("조사담당자 내역 조회 / 출동일지 ID '" + record_id + "'");
////        Optional<Record> optionalRecord = recordRepository.findById(record_id);
////        if (optionalRecord.isPresent()){
////            return responsibilRepository.findByRecord(optionalRecord.get());
////        }else{
////            return null;
////        }
////    }

}
