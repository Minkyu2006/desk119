package kr.co.broadwave.desk.record;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public RecordService(RecordRepository recordRepository,ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.recordRepository = recordRepository;
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

}
