package kr.co.broadwave.desk.statistics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Minkyu
 * Date : 2019-09-30
 * Remark :
 */

@Controller
@RequestMapping("/statistics")
public class statisticsController {

    //통계화면
    @RequestMapping("/statisticsview")
    public String statistics(){

        return "/statistics/statisticsview";
    }

    //통계화면 테스트
    @RequestMapping("/statisticsTest")
    public String statisticsTest(){

        return "/statistics/statisticsTest";
    }

}
