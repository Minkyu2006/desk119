package kr.co.broadwave.desk.statistics;

import kr.co.broadwave.desk.bscodes.LocationCityType;
import kr.co.broadwave.desk.mastercode.MasterCodeService;
import kr.co.broadwave.desk.record.Record;
import kr.co.broadwave.desk.record.RecordService;
import kr.co.broadwave.desk.record.responsibil.Responsibil;
import kr.co.broadwave.desk.record.responsibil.ResponsibilService;
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

    @Autowired
    public StatisticsController(RecordService recordService,
                                StatisticsService statisticsService) {
        this.recordService = recordService;
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
        List<String> nowYears = new ArrayList<>();
        List<String> productionYears = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();
        int nowYear = currentDate.getYear();
        int productionYear = currentDate.getYear()-1;

        records.forEach(x -> modefyDate.add(x.getModifyDateTime().getYear()));

        List<String> nowYearCitys = statisticsService.localyear(records,nowYears,modefyDate,nowYear);
        List<String> productionYearCitys = statisticsService.localyear2(records,productionYears,modefyDate,productionYear);

        // 현재/제작년도
        model.addAttribute("nowYear",nowYear);
        model.addAttribute("productionYear",productionYear);

        // 현재/제작년 지역별 건수
        model.addAttribute("nowYearCitys",nowYearCitys);
        model.addAttribute("productionYearCitys",productionYearCitys);

        return "/statistics/statisticsTest";
    }

}
