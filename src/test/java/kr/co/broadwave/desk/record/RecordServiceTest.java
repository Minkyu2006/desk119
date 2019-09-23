package kr.co.broadwave.desk.record;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * @author InSeok
 * Date : 2019-09-10
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

    @Test
    public void findById(){
        //given 테스트데이터 입력
        Record b1 = Record.builder()
                .arTitle("출동일지타이틀1")
                .arWriter("작성자1")
                .build();

        Record b2 = Record.builder()
                .arTitle("출동일지타이틀2")
                .arWriter("작성자2")
                .build();


        Record save1 = recordRepository.save(b1);
        Record save2 = recordRepository.save(b2);



        //when

        RecordMapperDto recordMapperDto = recordService.findById(save1.getId());
        //List<Record> records = recordRepository.findAll();
        //recordRepository.findByArTitle();

        //then
        assertThat(recordMapperDto.getArTitle()).as("타이틀반환 테스트 [ expect 출동일지타이틀1] ")
                .isEqualTo("출동일지타이틀1");

        //assertThat(records.size()).as("출동일지 전부조회 [ expect 2] ").isEqualTo(1);





    }

}

