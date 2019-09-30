package kr.co.broadwave.desk.statistics;

import kr.co.broadwave.desk.record.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Minkyu
 * Date : 2019-09-30
 * Remark :
 */

@Slf4j
@Controller
public class statisticsController {

    //통계화면
    @RequestMapping("/statistics")
    public String statistics(){

        return "statisticsPage";
    }

    //통계화면 테스트
    @RequestMapping("/statisticsTest")
    public String statisticsTest(){

        return "statisticsTestPage";
    }

}
