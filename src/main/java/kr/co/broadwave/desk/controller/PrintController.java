package kr.co.broadwave.desk.controller;

import kr.co.broadwave.desk.accounts.Account;
import kr.co.broadwave.desk.accounts.AccountService;
import kr.co.broadwave.desk.common.CommonUtils;
import kr.co.broadwave.desk.mastercode.MasterCodeService;
import kr.co.broadwave.desk.record.Record;
import kr.co.broadwave.desk.record.RecordService;
import kr.co.broadwave.desk.record.RecordViewDto;
import kr.co.broadwave.desk.record.file.RecordImageService;
import kr.co.broadwave.desk.record.file.RecordUploadFile;
import kr.co.broadwave.desk.record.file.RecordUploadFileDto;
import kr.co.broadwave.desk.record.file.RecordUploadFileRepository;
import kr.co.broadwave.desk.record.responsibil.Responsibil;
import kr.co.broadwave.desk.teams.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * @author Minkyu
 * Date : 2019-10-07
 * Remark :
 */

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
    @RequestMapping("/view/{id}")
    public String recordView(HttpServletRequest request, Model model, @PathVariable Long id){
        String currentuserid = CommonUtils.getCurrentuser(request);
        Optional<Account> account = accountService.findByUserid(currentuserid);
        String userid = account.get().getUserid();
        model.addAttribute("userid", userid);

        //데이터 가져오기
        RecordViewDto recordViewDto = recordService.findByIdView(id);
        model.addAttribute("record", recordViewDto);

        Optional<Record> record = recordService.findByIdRecord(id);
        if(record.isPresent()) {
            for(int i=0; i<4; i++){
                if(i!=3) {
                    RecordUploadFileDto recordUploadFileDto = recordimageService.recordUploadFile(record.get(), i+1);
//                    log.info("recordUploadFileDto : " + recordUploadFileDto);
                    if (recordUploadFileDto != null){
                        model.addAttribute("recorduploadFile"+i, recordUploadFileDto);
                        model.addAttribute("recordupload"+i, "ture");
                    }else{
                        model.addAttribute("recordupload"+i, "false");
                    }
                }else{
                    List<RecordUploadFileDto> recordUploadFileDto = recordimageService.recordUploadFileList(record.get(), 0);
//                    log.info("recordUploadFileDto : " + recordUploadFileDto);
                    if (recordUploadFileDto != null){
                        model.addAttribute("recorduploadFilesList", recordUploadFileDto);
                    }
                }
            }
        }

        List<Responsibil> responsibils = recordService.recordRespon(id);
        model.addAttribute("responsibils", responsibils);

        return "print/view";
    }

}
