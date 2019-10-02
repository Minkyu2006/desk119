package kr.co.broadwave.desk.statistics;

import kr.co.broadwave.desk.bscodes.CodeType;
import kr.co.broadwave.desk.mastercode.MasterCodeDto;
import kr.co.broadwave.desk.mastercode.MasterCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Minkyu
 * Date : 2019-09-30
 * Remark :
 */
@Slf4j
@Service
public class StatisticsService {
    private final MasterCodeService masterCodeService;

    @Autowired
    public StatisticsService(MasterCodeService masterCodeService) {
        this.masterCodeService = masterCodeService;
    }



    public List<String> relatedName(List<String> relatedIds) {




        return relatedIds;
    }

    public List<String> relatedCnt(List<String> relatedMasterCodes) {
//        int cnt1 = 0; int cnt2 = 0; int cnt3 = 0;
        List<String> masterCode = new ArrayList<>();
        List<MasterCodeDto> masterCodes = masterCodeService.findCodeList(CodeType.C0002);
        for(int i=0; i<masterCodes.size(); i++) {
            masterCode.add(masterCodes.get(i).getName());
        }
        System.out.println("마스터코드들 : "+masterCode);
        for(int i=0; i<relatedMasterCodes.size(); i++) {
            String cnt = relatedMasterCodes.get(i);
            if(cnt.matches("ㅇ")){
                //cnt1++;
            }else if(cnt.equals("타 기관")){
                //cnt2++;
            }else{
                //cnt3++;
            }
        }

        relatedMasterCodes.clear();
//        relatedIdCounts.add(Integer.toString(cnt1));
//        relatedIdCounts.add(Integer.toString(cnt2));
//        relatedIdCounts.add(Integer.toString(cnt3));

        return relatedMasterCodes;
    }




}
