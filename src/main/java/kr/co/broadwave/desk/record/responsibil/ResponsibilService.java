package kr.co.broadwave.desk.record.responsibil;

import kr.co.broadwave.desk.keygenerate.KeyGenerateService;
import kr.co.broadwave.desk.record.RecordRepository;
import kr.co.broadwave.desk.record.RecordRepositoryCustom;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Minkyu
 * Date : 2019-10-04
 * Remark :
 */
@Slf4j
@Service
public class ResponsibilService {
    private final ResponsibilRepository responsibilRepository;

    @Autowired
    public ResponsibilService(ResponsibilRepository responsibilRepository) {
        this.responsibilRepository = responsibilRepository;
    }


}
