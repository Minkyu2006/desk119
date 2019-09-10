package kr.co.broadwave.desk.controller;

import kr.co.broadwave.desk.bscodes.*;
import kr.co.broadwave.desk.mastercode.MasterCodeDto;
import kr.co.broadwave.desk.mastercode.MasterCodeService;
import kr.co.broadwave.desk.record.RecordDto;
import kr.co.broadwave.desk.record.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author MinKyu
 * Date : 2019-09-05
 * Remark : 출동일지 컨트롤러
 */
@Controller
@RequestMapping("/record")
public class RecordController {
    private final MasterCodeService masterCodeService;
    private final RecordService recordService;

    @Autowired
    public RecordController(MasterCodeService masterCodeService,RecordService recordService) {
        this.masterCodeService = masterCodeService;
        this.recordService = recordService;
    }

    @RequestMapping("/list")
    public String recordList(){
        return "record/recordlist";
    }

    @RequestMapping("/view")
    public String recordView(){
        return "record/recordview";
    }
    //모바일 페이지
    @RequestMapping("/mreg")
    public String morileregist(){
        return "mobile/mrecordreg";
    }

    @RequestMapping("/mview")
    public String recordMView(){
        return "mobile/mrecordview";
    }

    //출동일지작성
    @RequestMapping("recordreg")
    public String recordreg(Model model){
        System.out.println("등록화면호출");
        List<MasterCodeDto> arRelatedId = masterCodeService.findCodeList(CodeType.C0001);

        model.addAttribute("LocationCityTypes", LocationCityType.values());
        model.addAttribute("LocationAddressTypes", LocationAddressType.values());
        model.addAttribute("arRelatedIds", arRelatedId);
        return "record/recordreg";
    }

    //출동일지수정
    @RequestMapping("recordreg/{id}")
    public String noticeReg(Model model, @PathVariable Long id){
        RecordDto recordDto = recordService.findById(id);
        List<MasterCodeDto> arRelatedId = masterCodeService.findCodeList(CodeType.C0001);

        //List<UploadFile> uploadFiles = imageService.uploadFileList(id);
        model.addAttribute("arRelatedIds", arRelatedId);
        model.addAttribute("LocationCityTypes", LocationCityType.values());
        model.addAttribute("LocationAddressTypes", LocationAddressType.values());
        model.addAttribute("record", recordDto);
        //model.addAttribute("uploadFiles", uploadFiles);

        return "record/recordreg";
    }


}
