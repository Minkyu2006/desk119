package kr.co.broadwave.desk.statistics;

import kr.co.broadwave.desk.mastercode.MasterCodeService;
import kr.co.broadwave.desk.record.Record;
import kr.co.broadwave.desk.record.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    public String statisticsTest(Model model){
        List<Record> records = recordService.findAll();
        List<Integer> modefyDate = new ArrayList<>();
        List<String> city2019s = new ArrayList<>();
        List<String> city2018s = new ArrayList<>();

        records.forEach(x -> modefyDate.add(x.getModifyDateTime().getYear()));

        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int year2 = currentDate.getYear()-1;

        List<String> localyears = statisticsService.localyear(records,city2019s,modefyDate,year);
        List<String> localyears2 = statisticsService.localyear2(records,city2018s,modefyDate,year2);

        model.addAttribute("year",year);
        model.addAttribute("year2",year2);
        model.addAttribute("localyears",localyears);
        model.addAttribute("localyears2",localyears2);

        return "/statistics/statisticsTest";
    }

}
