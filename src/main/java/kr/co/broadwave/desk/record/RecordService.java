package kr.co.broadwave.desk.record;

import kr.co.broadwave.desk.notice.Notice;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    @Autowired
    public RecordService(RecordRepository recordRepository,
                         RecordRepositoryCustom recordRepositoryCustom,
                         ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.recordRepository = recordRepository;
        this.recordRepositoryCustom = recordRepositoryCustom;
    }

    public Record save(Record record){
        return recordRepository.save(record);
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



    public Optional<Record> findByArNumber(String arNumber){
        return recordRepository.findByArNumber(arNumber);
    }

    public Page<RecrodListDto> findAllBySearchStrings(String arNumber, String arTitle, String arWriter, Pageable pageable){
        return recordRepositoryCustom.findAllBySearchStrings(arNumber,arTitle,arWriter,pageable);

    }

    public void delete(Record record){
        recordRepository.delete(record);
    }

    public Optional<Record> findByIdRecord(Long id){
        return  recordRepository.findById(id);

    }
}
