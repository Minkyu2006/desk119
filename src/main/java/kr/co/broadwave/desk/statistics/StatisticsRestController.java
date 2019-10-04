package kr.co.broadwave.desk.statistics;

import kr.co.broadwave.desk.bscodes.CodeType;
import kr.co.broadwave.desk.common.AjaxResponse;
import kr.co.broadwave.desk.mastercode.MasterCodeDto;
import kr.co.broadwave.desk.mastercode.MasterCodeService;
import kr.co.broadwave.desk.record.Record;
import kr.co.broadwave.desk.record.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.*;

/**
 * @author Minkyu
 * Date : 2019-10-01
 * Remark :
 */
@Slf4j
@RestController
@RequestMapping("/api/statistics")
public class StatisticsRestController {
    private AjaxResponse res = new AjaxResponse();
    HashMap<String, Object> data = new HashMap<>();

    private final RecordService recordService;
    private final StatisticsService statisticsService;
    private final MasterCodeService masterCodeService;

    @Autowired
    public StatisticsRestController(RecordService recordService,
                                MasterCodeService masterCodeService,
                                StatisticsService statisticsService) {
        this.recordService = recordService;
        this.masterCodeService = masterCodeService;
        this.statisticsService = statisticsService;
    }

    @PostMapping("dataGraph")
    public ResponseEntity dataGraph(){

        List<Record> records = recordService.findAll();
        List<List<String>> circleDataColumns = new ArrayList<>(); // 원형 그래프데이터

        List<String> masters = new ArrayList<>();
        List<String> mastersSize = new ArrayList<>();
        List<String> masterCodeNames = new ArrayList<>();

        records.forEach(x -> masters.add(x.getArRelatedId().getName()));

        for(int i=0; i<masters.size(); i++){
            if(!mastersSize.contains(masters.get(i))){
                mastersSize.add(masters.get(i));
            }
        }

        //System.out.println("mastersSize 데이터 : "+mastersSize);

        int count = 0;
        for(int j=0; j<mastersSize.size(); j++) {
            String master = mastersSize.get(j);
            masterCodeNames.clear();
            for (int i = 0; i < mastersSize.size(); i++) {
                if (!masterCodeNames.contains(master)) {
                    masterCodeNames.add(master);
                }
            }
            for (int i = 0; i < masters.size(); i++) {
                if (masterCodeNames.contains(masters.get(i))) {
                    count++;
                }
            }
            masterCodeNames.add(Integer.toString(count));

//            System.out.println("masterCodeNames 데이터 : "+masterCodeNames);
//            System.out.println("건수 : "+count);

            int cnt = 0;
            int cnt2 = 1;
            circleDataColumns.add(Arrays.asList(masterCodeNames.get(cnt),masterCodeNames.get(cnt2)));
            count = 0;
        }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        List<List<String>> disastergraphDataColumns = new ArrayList<>();  // 재해재난 그래프데이터
        List<List<String>> facgraphDataColumns = new ArrayList<>();  // 조사시설물 그래프데이터
        List<List<String>> monthgraphDataColumns = new ArrayList<>(); // 월별 출동현황 그래프데이터

        LocalDate currentDate = LocalDate.now();
        int nowYear = currentDate.getYear();
        int productionYear = currentDate.getYear()-1;
        String now = Integer.toString(nowYear);
        String production = Integer.toString(productionYear);
        System.out.println("현재년도 : "+now);
        System.out.println("제작년도 : "+production);

        List<String> disasters = new ArrayList<>();
        List<String> facs = new ArrayList<>();
        List<String> monthDate = new ArrayList<>();
        records.forEach(x -> disasters.add(x.getArDisasterType()));
        records.forEach(x -> facs.add(x.getArFac()));
        records.forEach(x -> monthDate.add(x.getArIntoEnd()));
        System.out.println("재해분난 : "+disasters);
        System.out.println("조사시설물 : "+facs);
        System.out.println("월별건수 : "+monthDate);

        List<String> years = new ArrayList<>();
        List<String> months = new ArrayList<>();

        for(int i=0; i<monthDate.size(); i++){
            if(monthDate.get(i).equals("")){
                String stryear ="";
                String strmonth ="";
                years.add(stryear);
                months.add(strmonth);
            }else{
                String stryear =monthDate.get(i).substring(0,4);
                String strmonth =monthDate.get(i).substring(4,6);
                years.add(stryear);
                months.add(strmonth);
            }
        }
        System.out.println("년 : "+years);
        System.out.println("월 : "+months);

        List<String> nowDisastersCnt = new ArrayList<>();
        List<String> productionDisastersCnt = new ArrayList<>();

        List<String> nowFacCnt = new ArrayList<>();
        List<String> productionFacCnt = new ArrayList<>();

        int nowcnt01 = 0; int nowcnt02 = 0; int nowcnt03 = 0;
        int nowcnt04 = 0; int nowcnt05 = 0; int nowcnt06 = 0;
        int nowcnt07 = 0;

        int productioncnt01 = 0; int productioncnt02 = 0; int productioncnt03 = 0;
        int productioncnt04 = 0; int productioncnt05 = 0; int productioncnt06 = 0;
        int productioncnt07 = 0;

        for(int i=0; i<records.size(); i++) {
            if (years.get(i).equals(now)) {
                if(disasters.get(i).substring(0,1).equals("1")){
                    nowcnt01++;
                }
                if(disasters.get(i).substring(2,3).equals("1")){
                    nowcnt02++;
                }
                if(disasters.get(i).substring(4,5).equals("1")){
                    nowcnt03++;
                }
                if(disasters.get(i).substring(6,7).equals("1")){
                    nowcnt04++;
                }
                if(disasters.get(i).substring(8,9).equals("1")){
                    nowcnt05++;
                }
                if(disasters.get(i).substring(10,11).equals("1")){
                    nowcnt06++;
                }
                if(disasters.get(i).substring(12,13).equals("1")){
                    nowcnt07++;
                }
            }else if(years.get(i).equals(production)){
                if(disasters.get(i).substring(0,1).equals("1")){
                    productioncnt01++;
                }
                if(disasters.get(i).substring(2,3).equals("1")){
                    productioncnt02++;
                }
                if(disasters.get(i).substring(4,5).equals("1")){
                    productioncnt03++;
                }
                if(disasters.get(i).substring(6,7).equals("1")){
                    productioncnt04++;
                }
                if(disasters.get(i).substring(8,9).equals("1")){
                    productioncnt05++;
                }
                if(disasters.get(i).substring(10,11).equals("1")){
                    productioncnt06++;
                }
                if(disasters.get(i).substring(12,13).equals("1")){
                    productioncnt07++;
                }
            }
        }

        nowDisastersCnt.add(now);
        nowDisastersCnt.add(Integer.toString(nowcnt01));
        nowDisastersCnt.add(Integer.toString(nowcnt02));
        nowDisastersCnt.add(Integer.toString(nowcnt03));
        nowDisastersCnt.add(Integer.toString(nowcnt04));
        nowDisastersCnt.add(Integer.toString(nowcnt05));
        nowDisastersCnt.add(Integer.toString(nowcnt06));
        nowDisastersCnt.add(Integer.toString(nowcnt07));

        productionDisastersCnt.add(production);
        productionDisastersCnt.add(Integer.toString(productioncnt01));
        productionDisastersCnt.add(Integer.toString(productioncnt02));
        productionDisastersCnt.add(Integer.toString(productioncnt03));
        productionDisastersCnt.add(Integer.toString(productioncnt04));
        productionDisastersCnt.add(Integer.toString(productioncnt05));
        productionDisastersCnt.add(Integer.toString(productioncnt06));
        productionDisastersCnt.add(Integer.toString(productioncnt07));

        System.out.println("2019 재해분난 카운트 : "+nowDisastersCnt);
        System.out.println("2018 재해분난 카운트 : "+productionDisastersCnt);

        disastergraphDataColumns.add(nowDisastersCnt);
        disastergraphDataColumns.add(productionDisastersCnt);

        nowcnt01 = 0; nowcnt02 = 0; nowcnt03 = 0;
        nowcnt04 = 0; nowcnt05 = 0; nowcnt06 = 0;
        nowcnt07 = 0; int nowcnt08 = 0; int nowcnt09 = 0;
        int nowcnt10 = 0;

        productioncnt01 = 0; productioncnt02 = 0; productioncnt03 = 0;
        productioncnt04 = 0; productioncnt05 = 0; productioncnt06 = 0;
        productioncnt07 = 0; int productioncnt08 = 0; int productioncnt09 = 0;
        int productioncnt10 = 0;

        for(int i=0; i<records.size(); i++) {
            if (years.get(i).equals(now)) {
                if(facs.get(i).substring(0,1).equals("1")){
                    nowcnt01++;
                }
                if(facs.get(i).substring(2,3).equals("1")){
                    nowcnt02++;
                }
                if(facs.get(i).substring(4,5).equals("1")){
                    nowcnt03++;
                }
                if(facs.get(i).substring(6,7).equals("1")){
                    nowcnt04++;
                }
                if(facs.get(i).substring(8,9).equals("1")){
                    nowcnt05++;
                }
                if(facs.get(i).substring(10,11).equals("1")){
                    nowcnt06++;
                }
                if(facs.get(i).substring(12,13).equals("1")){
                    nowcnt07++;
                }
                if(facs.get(i).substring(14,15).equals("1")){
                    nowcnt08++;
                }
                if(facs.get(i).substring(16,17).equals("1")){
                    nowcnt09++;
                }
                if(facs.get(i).substring(18,19).equals("1")){
                    nowcnt10++;
                }
            }else if(years.get(i).equals(production)){
                if(facs.get(i).substring(0,1).equals("1")){
                    productioncnt01++;
                }
                if(facs.get(i).substring(2,3).equals("1")){
                    productioncnt02++;
                }
                if(facs.get(i).substring(4,5).equals("1")){
                    productioncnt03++;
                }
                if(facs.get(i).substring(6,7).equals("1")){
                    productioncnt04++;
                }
                if(facs.get(i).substring(8,9).equals("1")){
                    productioncnt05++;
                }
                if(facs.get(i).substring(10,11).equals("1")){
                    productioncnt06++;
                }
                if(facs.get(i).substring(12,13).equals("1")){
                    productioncnt07++;
                }
                if(facs.get(i).substring(14,15).equals("1")){
                    productioncnt08++;
                }
                if(facs.get(i).substring(16,17).equals("1")){
                    productioncnt09++;
                }
                if(facs.get(i).substring(18,19).equals("1")){
                    productioncnt10++;
                }
            }
        }

        nowFacCnt.add(now);
        nowFacCnt.add(Integer.toString(nowcnt01));
        nowFacCnt.add(Integer.toString(nowcnt02));
        nowFacCnt.add(Integer.toString(nowcnt03));
        nowFacCnt.add(Integer.toString(nowcnt04));
        nowFacCnt.add(Integer.toString(nowcnt05));
        nowFacCnt.add(Integer.toString(nowcnt06));
        nowFacCnt.add(Integer.toString(nowcnt07));
        nowFacCnt.add(Integer.toString(nowcnt08));
        nowFacCnt.add(Integer.toString(nowcnt09));
        nowFacCnt.add(Integer.toString(nowcnt10));

        productionFacCnt.add(production);
        productionFacCnt.add(Integer.toString(productioncnt01));
        productionFacCnt.add(Integer.toString(productioncnt02));
        productionFacCnt.add(Integer.toString(productioncnt03));
        productionFacCnt.add(Integer.toString(productioncnt04));
        productionFacCnt.add(Integer.toString(productioncnt05));
        productionFacCnt.add(Integer.toString(productioncnt06));
        productionFacCnt.add(Integer.toString(productioncnt07));
        productionFacCnt.add(Integer.toString(productioncnt08));
        productionFacCnt.add(Integer.toString(productioncnt09));
        productionFacCnt.add(Integer.toString(productioncnt10));

        System.out.println("2019 조사시설물 카운트 : "+nowFacCnt);
        System.out.println("2018 조사시설물 카운트 : "+productionFacCnt);

        facgraphDataColumns.add(nowFacCnt);
        facgraphDataColumns.add(productionFacCnt);

        nowcnt01 = 0; nowcnt02 = 0; nowcnt03 = 0;
        nowcnt04 = 0; nowcnt05 = 0; nowcnt06 = 0;
        nowcnt07 = 0; nowcnt08 = 0; nowcnt09 = 0;
        nowcnt10 = 0; int nowcnt11 = 0; int nowcnt12 = 0;

        productioncnt01 = 0; productioncnt02 = 0; productioncnt03 = 0;
        productioncnt04 = 0; productioncnt05 = 0; productioncnt06 = 0;
        productioncnt07 = 0; productioncnt08 = 0; productioncnt09 = 0;
        productioncnt10 = 0; int productioncnt11 = 0; int productioncnt12 = 0;

        for(int i=0; i<records.size(); i++) {
            if (years.get(i).equals(now)) {

                if (months.get(i).equals("01")) {
                    nowcnt01++;
                } else if (months.get(i).equals("02")) {
                    nowcnt02++;
                } else if (months.get(i).equals("03")) {
                    nowcnt03++;
                } else if (months.get(i).equals("04")) {
                    nowcnt04++;
                } else if (months.get(i).equals("05")) {
                    nowcnt05++;
                } else if (months.get(i).equals("06")) {
                    nowcnt06++;
                } else if (months.get(i).equals("07")) {
                    nowcnt07++;
                } else if (months.get(i).equals("08")) {
                    nowcnt08++;
                } else if (months.get(i).equals("09")) {
                    nowcnt09++;
                } else if (months.get(i).equals("10")) {
                    nowcnt10++;
                } else if (months.get(i).equals("11")) {
                    nowcnt11++;
                } else if (months.get(i).equals("12")) {
                    nowcnt12++;
                }
            } else if (years.get(i).equals(production)) {
                if (months.get(i).equals("01")) {
                    productioncnt01++;
                } else if (months.get(i).equals("02")) {
                    productioncnt02++;
                } else if (months.get(i).equals("03")) {
                    productioncnt03++;
                } else if (months.get(i).equals("04")) {
                    productioncnt04++;
                } else if (months.get(i).equals("05")) {
                    productioncnt05++;
                } else if (months.get(i).equals("06")) {
                    productioncnt06++;
                } else if (months.get(i).equals("07")) {
                    productioncnt07++;
                } else if (months.get(i).equals("08")) {
                    productioncnt08++;
                } else if (months.get(i).equals("09")) {
                    productioncnt09++;
                } else if (months.get(i).equals("10")) {
                    productioncnt10++;
                } else if (months.get(i).equals("11")) {
                    productioncnt11++;
                } else if (months.get(i).equals("12")) {
                    productioncnt12++;
                }
            }
        }



        List<String> nowCnt = new ArrayList<>();
        List<String> productionCnt = new ArrayList<>();

        nowCnt.add(now);
        nowCnt.add(Integer.toString(nowcnt01));
        nowCnt.add(Integer.toString(nowcnt02));
        nowCnt.add(Integer.toString(nowcnt03));
        nowCnt.add(Integer.toString(nowcnt04));
        nowCnt.add(Integer.toString(nowcnt05));
        nowCnt.add(Integer.toString(nowcnt06));
        nowCnt.add(Integer.toString(nowcnt07));
        nowCnt.add(Integer.toString(nowcnt08));
        nowCnt.add(Integer.toString(nowcnt09));
        nowCnt.add(Integer.toString(nowcnt10));
        nowCnt.add(Integer.toString(nowcnt11));
        nowCnt.add(Integer.toString(nowcnt12));

        productionCnt.add(production);
        productionCnt.add(Integer.toString(productioncnt01));
        productionCnt.add(Integer.toString(productioncnt02));
        productionCnt.add(Integer.toString(productioncnt03));
        productionCnt.add(Integer.toString(productioncnt04));
        productionCnt.add(Integer.toString(productioncnt05));
        productionCnt.add(Integer.toString(productioncnt06));
        productionCnt.add(Integer.toString(productioncnt07));
        productionCnt.add(Integer.toString(productioncnt08));
        productionCnt.add(Integer.toString(productioncnt09));
        productionCnt.add(Integer.toString(productioncnt10));
        productionCnt.add(Integer.toString(productioncnt11));
        productionCnt.add(Integer.toString(productioncnt12));

        System.out.println("현재 카운트 : "+nowCnt);
        System.out.println("작년 카운트 : "+productionCnt);

        monthgraphDataColumns.add(nowCnt);
        monthgraphDataColumns.add(productionCnt);

        System.out.println("출동요청기관 데이터 : "+circleDataColumns);
        System.out.println("재해재난유형 데이터 : "+disastergraphDataColumns);
        System.out.println("조사시설물 데이터 : "+facgraphDataColumns);
        System.out.println("월별 출동 현황 데이터 : "+monthgraphDataColumns);





        List<String> teamgraphDataColumns = new ArrayList<>();  // 부서별 출동현황 그래프데이터
        List<String> teamsData = new ArrayList<>();  // 부서별 출동현황 그래프데이터

        data.clear();

        // 원형 그래프데이터
        data.put("circle_data_columns",circleDataColumns);
        // 재해재난 그래프데이터
        data.put("disaster_data_columns",disastergraphDataColumns);
        // 조사시설물 그래프데이터
        data.put("fac_data_columns",facgraphDataColumns);
        // 부서별 출동현황 그래프데이터
        data.put("team_data_columns",teamgraphDataColumns);
        data.put("teamsData",teamsData);
        // 월별 출동현황 그래프데이터
        data.put("month_data_columns",monthgraphDataColumns);

        res.addResponse("data",data);
        return ResponseEntity.ok(res.success());
    }

}
