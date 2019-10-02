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

    @PostMapping("circleGraph")
    public ResponseEntity circleGraph(){
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int year2 = currentDate.getYear()-1;
        System.out.println("현재년도 : "+year);
        System.out.println("제작년도 : "+year2);

        List<Record> records = recordService.findAll();

        // 2차원리스트 그래프데이터
        List<List<String>> graphDataColumns = new ArrayList<>();

        List<String> masters = new ArrayList<>();
        List<String> mastersSize = new ArrayList<>();
        List<String> masterCodeNames = new ArrayList<>();

        records.forEach(x -> masters.add(x.getArRelatedId().getName()));

        for(int i=0; i<masters.size(); i++){
            if(!mastersSize.contains(masters.get(i))){
                mastersSize.add(masters.get(i));
            }
        }
        System.out.println("mastersSize 데이터 : "+mastersSize);

        int count = 0;
        for(int j=0; j<mastersSize.size(); j++) {
            String master = mastersSize.get(j);
            masterCodeNames.clear();
            for (int i = 0; i < mastersSize.size(); i++) {
                if (!masterCodeNames.contains(master)) {
                    masterCodeNames.add(master);
                    break;
                }
            }
            for (int i = 0; i < masters.size(); i++) {
                if (masterCodeNames.contains(masters.get(i))) {
                    count++;
                }
            }
            masterCodeNames.add(Integer.toString(count));

            System.out.println("masterCodeNames 데이터 : "+masterCodeNames);
            System.out.println("건수 : "+count);

            int cnt = 0;
            int cnt2 = 1;
            graphDataColumns.add(Arrays.asList(masterCodeNames.get(cnt),masterCodeNames.get(cnt2)));
            count = 0;
        }

        System.out.println("graphDataColumns 데이터 : "+graphDataColumns);

        data.clear();
        data.put("datacolumns",graphDataColumns);
        res.addResponse("data",data);
        return ResponseEntity.ok(res.success());
    }

    @PostMapping("mapGraph")
    public ResponseEntity mapGraph(){


        return ResponseEntity.ok(res.success());
    }
}
