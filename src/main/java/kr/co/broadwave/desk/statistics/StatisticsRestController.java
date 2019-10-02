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
        List<Record> records = recordService.findAll();
        List<MasterCodeDto> masterCodes = masterCodeService.findCodeList(CodeType.C0002);
        System.out.println("전체 레코드: "+records);
        System.out.println("전체 마스터코드 "+masterCodes);
        List<String> masterCodeNames = new ArrayList<>();
        for (int i=0; i<masterCodes.size(); i++){
            masterCodeNames.add(masterCodes.get(i).getName());
        }
        System.out.println("전체 관련부처 "+masterCodeNames);

        List<List<String>> graphDataColumns = new ArrayList<>();
        List<String> relatedCodeLists = new ArrayList<>();
        List<String> relatedMasterCodes = new ArrayList<>();
        for(int i=0; i<records.size(); i++){
            relatedCodeLists.add(records.get(i).getArRelatedId().getCode());
            relatedMasterCodes.add(records.get(i).getArRelatedId().getName());
        }
        System.out.println("relatedMasterCodes : "+relatedMasterCodes);
        List<String> relatedIds = statisticsService.relatedName(relatedCodeLists);
        List<String> relatedIdCounts = statisticsService.relatedCnt(relatedMasterCodes);
        System.out.println("relatedCodeLists : "+relatedCodeLists);
        System.out.println("relatedIdCounts 바뀌기 후 : "+relatedIdCounts);

        System.out.println("graphDataColumns : "+graphDataColumns);


        data.clear();
        data.put("datacolumns",graphDataColumns);
        res.addResponse("data",data);
        return ResponseEntity.ok(res.success());
    }

}
