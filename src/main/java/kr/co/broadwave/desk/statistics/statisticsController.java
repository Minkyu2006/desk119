package kr.co.broadwave.desk.statistics;

import kr.co.broadwave.desk.accounts.AccountService;
import kr.co.broadwave.desk.accounts.LoginlogService;
import kr.co.broadwave.desk.mastercode.MasterCodeService;
import kr.co.broadwave.desk.record.Record;
import kr.co.broadwave.desk.record.RecordService;
import kr.co.broadwave.desk.teams.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Minkyu
 * Date : 2019-09-30
 * Remark :
 */

@Controller
@RequestMapping("/statistics")
public class statisticsController {

    private final RecordService recordService;

    @Autowired
    public statisticsController(RecordService recordService) {
        this.recordService = recordService;
    }

    //통계화면
    @RequestMapping("/statisticsview")
    public String statistics(Model model){
        List<Record> records = recordService.findAll();



        return "/statistics/statisticsview";
    }

    //통계화면 테스트
    @RequestMapping("/statisticsTest")
    public String statisticsTest(Model model){
        List<Record> records = recordService.findAll();
        System.out.println("전체 레코드: "+records);
        for (int i = 0; i<records.size(); i++) {
            List<String> b = new ArrayList<>();
            LocalDateTime a = records.get(i).getModifyDateTime();
            System.out.println("에이 : "+a);
        }

        return "/statistics/statisticsTest";
    }

}
