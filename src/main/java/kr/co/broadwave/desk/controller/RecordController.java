package kr.co.broadwave.desk.controller;

import kr.co.broadwave.desk.bscodes.*;
import kr.co.broadwave.desk.mastercode.MasterCodeDto;
import kr.co.broadwave.desk.mastercode.MasterCodeService;
import kr.co.broadwave.desk.record.*;
import kr.co.broadwave.desk.record.file.RecordImageService;
import kr.co.broadwave.desk.record.file.RecordUploadFile;
import kr.co.broadwave.desk.record.file.RecordUploadFileRepository;
import kr.co.broadwave.desk.record.responsibil.Responsibil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;

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
    private final RecordUploadFileRepository recorduploadFileRepository;
    private final RecordImageService recordimageService;
    private final RecordRepository recordRepository;

    @Autowired
    public RecordController(
            MasterCodeService masterCodeService,
            RecordService recordService,
            RecordImageService recordimageService,
            RecordRepository recordRepository,
            RecordUploadFileRepository recorduploadFileRepository) {
        this.masterCodeService = masterCodeService;
        this.recordService = recordService;
        this.recorduploadFileRepository = recorduploadFileRepository;
        this.recordimageService = recordimageService;
        this.recordRepository = recordRepository;
    }

    //모바일 작성페이지
    @RequestMapping("/mreg")
    public String recordMReg(Model model){
        System.out.println("등록화면호출");

        List<MasterCodeDto> arRelatedId = masterCodeService.findCodeList(CodeType.C0002);

        model.addAttribute("LocationCityTypes", LocationCityType.values());
        model.addAttribute("LocationAddressTypes", LocationAddressType.values());
        model.addAttribute("arRelatedIds", arRelatedId);

        return "mobile/mrecordreg";
    }

    @RequestMapping("mreg/{id}")
    public String recordMUpdate(Model model, @PathVariable Long id){

        RecordMapperDto recordMapperDto = recordService.findById(id);
        List<MasterCodeDto> arRelatedId = masterCodeService.findCodeList(CodeType.C0002);
        List<RecordUploadFile> recorduploadFiles = recordimageService.recorduploadFileList(id);
        List<Responsibil> responsibils = recordService.recordRespon(id);

        // 날짜 데이터변경(update)
        String into_start = recordMapperDto.getArIntoStart();
        String into_end = recordMapperDto.getArIntoEnd();

        StringBuffer start = new StringBuffer(into_start);
        StringBuffer end = new StringBuffer(into_end);
        start.insert(4,"-");
        start.insert(7,"-");
        end.insert(4,"-");
        end.insert(7,"-");
        String startSet = start.toString();
        String endSet = end.toString();
        recordMapperDto.setArIntoStart(startSet);
        recordMapperDto.setArIntoEnd(endSet);

        model.addAttribute("arRelatedIds", arRelatedId);
        model.addAttribute("LocationCityTypes", LocationCityType.values());
        model.addAttribute("LocationAddressTypes", LocationAddressType.values());
        model.addAttribute("record", recordMapperDto);
        model.addAttribute("recorduploadFiles", recorduploadFiles);
        model.addAttribute("responsibils", responsibils);

        return "mobile/mrecordreg";
    }

    // 모바일 뷰페이지
    @RequestMapping("/mview/{id}")
    public String recordMView(Model model, @PathVariable Long id){

        RecordViewDto recordViewDto = recordService.findByIdView(id);
        List<RecordUploadFile> recorduploadFiles = recordimageService.recorduploadFileList(id);
        List<Responsibil> responsibils = recordService.recordRespon(id);

        model.addAttribute("record", recordViewDto);
        model.addAttribute("recorduploadFiles", recorduploadFiles);
        model.addAttribute("responsibils", responsibils);

        return "mobile/mrecordview";
    }

    //출동일지작성
    @RequestMapping("reg")
    public String recordreg(Model model){
        System.out.println("등록화면호출");
        List<MasterCodeDto> arRelatedId = masterCodeService.findCodeList(CodeType.C0002);

        model.addAttribute("LocationCityTypes", LocationCityType.values());
        model.addAttribute("LocationAddressTypes", LocationAddressType.values());
        model.addAttribute("arRelatedIds", arRelatedId);

        return "record/recordreg";
    }

    //출동일지수정
    @RequestMapping("reg/{id}")
    public String recrodReg(Model model, @PathVariable Long id){
        RecordMapperDto recordMapperDto = recordService.findById(id);
        List<MasterCodeDto> arRelatedId = masterCodeService.findCodeList(CodeType.C0002);

        model.addAttribute("arRelatedIds", arRelatedId);
        model.addAttribute("LocationCityTypes", LocationCityType.values());
        model.addAttribute("LocationAddressTypes", LocationAddressType.values());
        model.addAttribute("record", recordMapperDto);

        List<RecordUploadFile> recorduploadFiles = recordimageService.recorduploadFileList(id);
        model.addAttribute("recorduploadFiles", recorduploadFiles);

        List<Responsibil> responsibils = recordService.recordRespon(id);
        model.addAttribute("responsibils", responsibils);

        return "record/recordreg";
    }

    // 출동일지 리스트
    @RequestMapping("/list")
    public String recordList(){
        return "record/recordlist";
    }

    // 뷰페이지
    @RequestMapping("view/{id}")
    public String recordView(Model model, @PathVariable Long id){

        //데이터 가져오기
        RecordViewDto recordViewDto = recordService.findByIdView(id);
        model.addAttribute("record", recordViewDto);

        List<RecordUploadFile> recorduploadFiles = recordimageService.recorduploadFileList(id);
        model.addAttribute("recorduploadFiles", recorduploadFiles);

        List<Responsibil> responsibils = recordService.recordRespon(id);
        model.addAttribute("responsibils", responsibils);

        return "record/recordview";
    }

    // 파일다운로드 컨트롤러
    @RequestMapping("download/{fileid}")
    @ResponseBody
    public byte[] downProcess(HttpServletResponse response,
                              @PathVariable Long fileid) throws IOException {

        Optional<RecordUploadFile> optionalUploadFile = recorduploadFileRepository.findById(fileid);
        String filePath = optionalUploadFile.get().getAfFilePath();
        String filename = URLEncoder.encode(optionalUploadFile.get().getAfFileName(),"UTF-8").replaceAll("\\+", "%20");
        File file = new File(filePath);
        byte[] bytes = FileCopyUtils.copyToByteArray(file);

        response.setHeader("Content-Disposition",
                "attachment;filename=\"" + filename + "\"");
        response.setContentLength(bytes.length);
        return bytes;
    }

}
