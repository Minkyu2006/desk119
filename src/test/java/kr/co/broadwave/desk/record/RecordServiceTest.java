package kr.co.broadwave.desk.record;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * @author Minkyi
 * Date : 2021-04-15
 * Remark :
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RecordServiceTest {

    @Autowired
    RecordRepository recordRepository;

    @Autowired
    RecordService recordService;

    // RecordService save(Record record) 테스트
    @Test
    public void saveTest(){
        Record record = new Record();
    }


}

