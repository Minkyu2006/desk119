package kr.co.broadwave.desk.controller;

import kr.co.broadwave.desk.accounts.AccountService;
import kr.co.broadwave.desk.common.AjaxResponse;
import kr.co.broadwave.desk.record.Record;
import kr.co.broadwave.desk.record.RecordService;
import kr.co.broadwave.desk.record.RecordViewDto;
import kr.co.broadwave.desk.record.RecordViewPrintDto;
import kr.co.broadwave.desk.record.file.RecordImageService;
import kr.co.broadwave.desk.record.file.RecordUploadFileDto;
import kr.co.broadwave.desk.record.responsibil.Responsibil;
import kr.co.broadwave.desk.record.responsibil.ResponsibilListDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

/**
 * @author Minkyu
 * Date : 2019-10-07
 * Remark :
 */

@Slf4j
@Controller
@RequestMapping("/print")
public class PrintController {
    private final RecordService recordService;
    private final RecordImageService recordimageService;
    private final AccountService accountService;

    @Autowired
    public PrintController(
            RecordService recordService,
            RecordImageService recordimageService,
            AccountService accountService) {
        this.accountService = accountService;
        this.recordService = recordService;
        this.recordimageService = recordimageService;
    }

    //프린터 인쇄화면 전용컨트롤러
    @RequestMapping("/oneview/{id}")
    public String oneview(Model model, @PathVariable Long id){

        //데이터 가져오기
        RecordViewDto recordViewDto = recordService.findByIdView(id);
        model.addAttribute("record", recordViewDto);

        for(int i=0; i<4; i++){
            if(i!=3) {
                RecordUploadFileDto recordUploadFileDto = recordimageService.recordUploadFile(id, i+1);
//                    log.info("recordUploadFileDto : " + recordUploadFileDto);
                if (recordUploadFileDto != null){
                    model.addAttribute("recorduploadFile"+i, recordUploadFileDto);
                }
            }else{
                List<RecordUploadFileDto> recordUploadFileDto = recordimageService.recordUploadFileList(id, 0);
//                    log.info("recordUploadFileDto : " + recordUploadFileDto);
                if (recordUploadFileDto != null){
                    model.addAttribute("recorduploadFilesList", recordUploadFileDto);
                }
            }
        }

        List<Responsibil> responsibils = recordService.recordRespon(id);
        model.addAttribute("responsibils", responsibils);

        return "print/oneview";
    }

    //프린터 인쇄화면 전용컨트롤러(멀티)
    @RequestMapping("/multiview/{id}")
    public String multiview(Model model, @PathVariable Long[] id){

        List<Long> ids = new ArrayList<>(Arrays.asList(id));

        //아이디값들 보내기
        model.addAttribute("ids", ids);

        return "print/multiview";
    }

    // 프린트할 아이디값받아 정보뿌리기(멀티)
    @PostMapping("multiViewInfo")
    public ResponseEntity<Map<String,Object>> multiViewInfo(Model model,
                                                   @RequestParam(value="ids[]", defaultValue="") List<Long> ids){

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        //데이터 가져오기
        List<RecordViewPrintDto> recordViewDto = recordService.findByIdViewList(ids);
        data.put("record", recordViewDto);

        List<Long> idsList = new ArrayList<>();
        for(int i=0; i<recordViewDto.size(); i++){
            idsList.add(recordViewDto.get(i).getId());
        }
        //log.info("recordList : "+recordList);
        data.put("idsList", idsList);

        List<ResponsibilListDto> responsibils = recordService.recordResponList(idsList);
        data.put("responsibils", responsibils);

        for(int i=0; i<4; i++){
            if(i!=3) {
                List<RecordUploadFileDto> recordUploadFilePrint = recordimageService.recordUploadFilePrint(idsList, i+1);
//                log.info("recordUploadFilePrint : " + recordUploadFilePrint);
                if (recordUploadFilePrint != null){
                     data.put("recordUploadFilePrint"+i, recordUploadFilePrint);
                }
            }else{
                List<RecordUploadFileDto> recordUploadFileListPrint = recordimageService.recordUploadFilePrint(idsList, 0);
//                log.info("recordUploadFileListPrint : " + recordUploadFileListPrint);
                if (recordUploadFileListPrint != null){
                     data.put("recordUploadFileListPrint", recordUploadFileListPrint);
                }
            }
        }

//        log.info("recordViewDto : "+recordViewDto);
//        log.info("responsibils : "+responsibils);

        res.addResponse("data",data);

        return ResponseEntity.ok(res.success());
    }

}
