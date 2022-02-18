package kr.co.broadwave.desk.record.responsibil;

import kr.co.broadwave.desk.keygenerate.KeyGenerateService;
import kr.co.broadwave.desk.record.Record;
import kr.co.broadwave.desk.record.RecordRepository;
import kr.co.broadwave.desk.record.RecordRepositoryCustom;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Minkyu
 * Date : 2019-11-20
 * Remark :
 */
@Slf4j
@Service
public class ResponsibilService {
    private final ResponsibilRepository responsibilRepository;
    private final ResponsibilRepositoryCustom responsibilRepositoryCustom;
    @Autowired
    public ResponsibilService(ResponsibilRepository responsibilRepository,
                              ResponsibilRepositoryCustom responsibilRepositoryCustom) {
        this.responsibilRepository = responsibilRepository;
        this.responsibilRepositoryCustom = responsibilRepositoryCustom;
    }

    public void delete(Responsibil responsibil) {
        responsibilRepository.delete(responsibil);
    }

    public long resDel(Record record) {
        return responsibilRepositoryCustom.resDel(record);
    }
}
