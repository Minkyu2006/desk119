package kr.co.broadwave.desk.statistics;

import kr.co.broadwave.desk.bscodes.CodeType;
import kr.co.broadwave.desk.mastercode.MasterCodeDto;
import kr.co.broadwave.desk.mastercode.MasterCodeService;
import kr.co.broadwave.desk.record.Record;
import kr.co.broadwave.desk.record.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

import static kr.co.broadwave.desk.bscodes.CodeType.C0002;

/**
 * @author Minkyu
 * Date : 2019-09-30
 * Remark :
 */

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

    private final RecordService recordService;
    private final StatisticsService statisticsService;
    private final MasterCodeService masterCodeService;

    @Autowired
    public StatisticsController(RecordService recordService,
                                MasterCodeService masterCodeService,
                                StatisticsService statisticsService) {
        this.recordService = recordService;
        this.masterCodeService = masterCodeService;
        this.statisticsService = statisticsService;
    }

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
