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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        System.out.println("레스트컨트롤러 진입!");
        List<Record> records = recordService.findAll();
        System.out.println("전체 레코드: "+records);

        List<List<Integer>> graphDataColumns = new ArrayList<>();



        data.clear();
        data.put("datacolumns",graphDataColumns);
        res.addResponse("data",data);
        return ResponseEntity.ok(res.success());
    }

}
