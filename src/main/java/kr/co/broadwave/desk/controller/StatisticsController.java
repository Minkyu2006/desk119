package kr.co.broadwave.desk.controller;

import kr.co.broadwave.desk.record.Record;
import kr.co.broadwave.desk.record.RecordService;
import kr.co.broadwave.desk.record.RecrodStatisticDto;
import kr.co.broadwave.desk.statistics.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Minkyu
 * Date : 2019-09-30
 * Remark :
 */
@Slf4j
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
    public String statistics(Model model){
        LocalDate currentDate = LocalDate.now();
        int nowYear = currentDate.getYear();
        int productionYear = currentDate.getYear()-1;

        List<Record> records = recordService.findAll();
        List<String> nowYearsCity = new ArrayList<>();
        List<String> productionYearsCity = new ArrayList<>();
        List<Integer> arState  = new ArrayList<>();
        List<String> intoEnd = new ArrayList<>();
        List<String> modefyDate = new ArrayList<>();
        List<String> stateOneCity = new ArrayList<>();
        records.forEach(x -> arState.add(x.getArRecordState()));

        for (int i=0; i<arState.size(); i++) {
            if (arState.get(i) == 1) {
                intoEnd.add(records.get(i).getArIntoEnd());
                stateOneCity.add(records.get(i).getArLocationCityType().getDesc());
            }
        }
        for(int i=0; i<intoEnd.size(); i++){
            String stryear =intoEnd.get(i).substring(0,4);
            modefyDate.add(stryear);
        }
        for(int i=0; i<stateOneCity.size(); i++) {
            if(modefyDate.get(i).equals(Integer.toString(nowYear))){
                nowYearsCity.add(stateOneCity.get(i));
            }else if(modefyDate.get(i).equals(Integer.toString(productionYear))){
                productionYearsCity.add(stateOneCity.get(i));
            }
        }

        List<String> nowYearCity = statisticsService.localyear(nowYearsCity);
        List<String> productionYearCity = statisticsService.localyear2(productionYearsCity);

        // 현재/제작년도
        model.addAttribute("nowYear",nowYear);
        model.addAttribute("productionYear",productionYear);

        // 현재/제작년 지역별 건수
        model.addAttribute("nowYearCitys",nowYearCity);
        model.addAttribute("productionYearCitys",productionYearCity);

        return "statistics/statisticsview";
    }

    //통계화면(유형별)
    @RequestMapping("/statisticsviewtype/{typeName}/{num}")
    public String statisticsviewtype(Model model,@PathVariable String typeName,@PathVariable String num){
//        log.info("typeName : "+typeName);
//        log.info("num : "+num);
        model.addAttribute("typeName",typeName);
        model.addAttribute("num",num);
        LocalDate currentDate = LocalDate.now();
        int nowYear = currentDate.getYear();
        int productionYear = currentDate.getYear()-1;

        List<RecrodStatisticDto> records = recordService.findByStatisticList2(typeName, num);
        List<String> nowYearsCity = new ArrayList<>();
        List<String> productionYearsCity = new ArrayList<>();
        List<Integer> arState  = new ArrayList<>();
        List<String> intoEnd = new ArrayList<>();
        List<String> modefyDate = new ArrayList<>();
        List<String> stateOneCity = new ArrayList<>();
        records.forEach(x -> arState.add(x.getArRecordState()));

        for (int i=0; i<arState.size(); i++) {
            if (arState.get(i) == 1) {
                intoEnd.add(records.get(i).getArIntoEnd());
                stateOneCity.add(records.get(i).getArLocationCityType());
            }
        }
        for(int i=0; i<intoEnd.size(); i++){
            String stryear =intoEnd.get(i).substring(0,4);
            modefyDate.add(stryear);
        }
        for(int i=0; i<stateOneCity.size(); i++) {
            if(modefyDate.get(i).equals(Integer.toString(nowYear))){
                nowYearsCity.add(stateOneCity.get(i));
            }else if(modefyDate.get(i).equals(Integer.toString(productionYear))){
                productionYearsCity.add(stateOneCity.get(i));
            }
        }

        List<String> nowYearCity = statisticsService.localyear(nowYearsCity);
        List<String> productionYearCity = statisticsService.localyear2(productionYearsCity);

        // 현재/제작년도
        model.addAttribute("nowYear",nowYear);
        model.addAttribute("productionYear",productionYear);

        // 현재/제작년 지역별 건수
        model.addAttribute("nowYearCitys",nowYearCity);
        model.addAttribute("productionYearCitys",productionYearCity);

        return "statistics/statisticsviewtype";
    }

}
