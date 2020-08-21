package kr.co.broadwave.desk.controller;

import kr.co.broadwave.desk.accounts.Account;
import kr.co.broadwave.desk.accounts.AccountService;
import kr.co.broadwave.desk.bscodes.CodeType;
import kr.co.broadwave.desk.bscodes.LocationAddressType;
import kr.co.broadwave.desk.bscodes.LocationCityType;
import kr.co.broadwave.desk.common.CommonUtils;
import kr.co.broadwave.desk.mastercode.MasterCodeDto;
import kr.co.broadwave.desk.mastercode.MasterCodeService;
import kr.co.broadwave.desk.record.Record;
import kr.co.broadwave.desk.record.RecordMapperDto;
import kr.co.broadwave.desk.record.RecordService;
import kr.co.broadwave.desk.record.RecordViewDto;
import kr.co.broadwave.desk.record.file.RecordImageService;
import kr.co.broadwave.desk.record.file.RecordUploadFile;
import kr.co.broadwave.desk.record.file.RecordUploadFileDto;
import kr.co.broadwave.desk.record.file.RecordUploadFileRepository;
import kr.co.broadwave.desk.record.file.mobilefile.MobileImageService;
import kr.co.broadwave.desk.record.file.mobilefile.MobileUploadFile;
import kr.co.broadwave.desk.record.responsibil.Responsibil;
import kr.co.broadwave.desk.teams.TeamDto;
import kr.co.broadwave.desk.teams.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;

/**
 * @author MinKyu
 * Date : 2019-09-05
 * Remark : 출동일지 컨트롤러
 */
@Slf4j
@Controller
@RequestMapping("/record")
public class RecordController {
    private final MasterCodeService masterCodeService;
    private final RecordService recordService;
    private final RecordUploadFileRepository recorduploadFileRepository;
    private final RecordImageService recordimageService;
    private final TeamService teamService;
    private final AccountService accountService;
    private final MobileImageService mobileImageService;

    @Autowired
    public RecordController(
            MasterCodeService masterCodeService,
            RecordService recordService,
            RecordImageService recordimageService,
            TeamService teamService,
            AccountService accountService,
            RecordUploadFileRepository recorduploadFileRepository,
            MobileImageService mobileImageService) {
        this.masterCodeService = masterCodeService;
        this.accountService = accountService;
        this.recordService = recordService;
        this.recorduploadFileRepository = recorduploadFileRepository;
        this.recordimageService = recordimageService;
        this.teamService = teamService;
        this.mobileImageService = mobileImageService;
    }

    //모바일 작성페이지
    @RequestMapping("/mreg")
    public String recordMReg(Model model){
        List<MasterCodeDto> arRelatedId = masterCodeService.findCodeList(CodeType.C0002);
        List<TeamDto> teams = teamService.findTeamList();

        model.addAttribute("recordupload0", "false");
        model.addAttribute("recordupload1", "false");
        model.addAttribute("recordupload2", "false");

        model.addAttribute("teams", teams);
        model.addAttribute("LocationCityTypes", LocationCityType.values());
        model.addAttribute("LocationAddressTypes", LocationAddressType.values());
        model.addAttribute("arRelatedIds", arRelatedId);

        return "mobile/mrecordreg";
    }

    @RequestMapping("mreg/{id}")
    public String recordMUpdate(Model model, @PathVariable Long id){
        RecordMapperDto recordMapperDto = recordService.findById(id);
        List<MasterCodeDto> arRelatedId = masterCodeService.findCodeList(CodeType.C0002);
        List<Responsibil> responsibils = recordService.recordRespon(id);
        List<TeamDto> teams = teamService.findTeamList();

        for(int i=0; i<4; i++){
            if(i!=3) {
                RecordUploadFileDto recordUploadFileDto = recordimageService.recordUploadFile(id, i+1);
//                    log.info("recordUploadFileDto : " + recordUploadFileDto);
                if (recordUploadFileDto != null){
                    model.addAttribute("recorduploadFile"+i, recordUploadFileDto);
                    model.addAttribute("recordupload"+i, "ture");
                }else{
                    model.addAttribute("recorduploadFile"+i, null);
                    model.addAttribute("recordupload"+i, "false");
                }
            }else{
                List<RecordUploadFileDto> recordUploadFileDto = recordimageService.recordUploadFileList(id, 0);
//                    log.info("recordUploadFileDto : " + recordUploadFileDto);
                if (recordUploadFileDto != null){
                    model.addAttribute("recorduploadFilesList", recordUploadFileDto);
                }
            }
        }

        // 날짜 데이터변경(update)
        String intoStart = recordMapperDto.getArIntoStart();
        String intoEnd = recordMapperDto.getArIntoEnd();
        if(!intoStart.equals("")) {
            StringBuffer start = new StringBuffer(intoStart);
            start.insert(4, "-");
            start.insert(7, "-");
            String startSet = start.toString();
            recordMapperDto.setArIntoStart(startSet);
        }
        if(!intoEnd.equals("")) {
            StringBuffer end = new StringBuffer(intoEnd);
            end.insert(4, "-");
            end.insert(7, "-");
            String endSet = end.toString();
            recordMapperDto.setArIntoEnd(endSet);
        }

        model.addAttribute("teams", teams);
        model.addAttribute("arRelatedIds", arRelatedId);
        model.addAttribute("LocationCityTypes", LocationCityType.values());
        model.addAttribute("LocationAddressTypes", LocationAddressType.values());
        model.addAttribute("record", recordMapperDto);
        model.addAttribute("responsibils", responsibils);

        return "mobile/mrecordreg";
    }

    // 모바일 뷰페이지
    @RequestMapping("/mview/{id}")
    public String recordMView(HttpServletRequest request,Model model, @PathVariable Long id){
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
        }

        List<Responsibil> responsibils = recordService.recordRespon(id);
        model.addAttribute("responsibils", responsibils);

        return "mobile/mrecordview";
    }

    //출동일지작성
    @RequestMapping("reg")
    public String recordreg(Model model) {
        List<MasterCodeDto> arRelatedId = masterCodeService.findCodeList(CodeType.C0002);
        List<TeamDto> teams = teamService.findTeamList();

        model.addAttribute("recordupload0", "false");
        model.addAttribute("recordupload1", "false");
        model.addAttribute("recordupload2", "false");

        model.addAttribute("teams", teams);
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
        model.addAttribute("record", recordMapperDto);

        Optional<Record> record = recordService.findByIdRecord(id);
        if(record.isPresent()) {
            for(int i=0; i<4; i++){
                if(i!=3) {
                    RecordUploadFileDto recordUploadFileDto = recordimageService.recordUploadFile(id, i+1);
//                    log.info("recordUploadFileDto : " + recordUploadFileDto);
                    if (recordUploadFileDto != null){
                        model.addAttribute("recorduploadFile"+i, recordUploadFileDto);
                        model.addAttribute("recordupload"+i, "ture");
                    }else{
                        model.addAttribute("recorduploadFile"+i, null);
                        model.addAttribute("recordupload"+i, "false");
                    }
                }else{
                    List<RecordUploadFileDto> recordUploadFileDto = recordimageService.recordUploadFileList(id, 0);
//                    log.info("recordUploadFileDto : " + recordUploadFileDto);
                    if (recordUploadFileDto != null){
                        model.addAttribute("recorduploadFilesList", recordUploadFileDto);
                    }
                }
            }
        }

        List<Responsibil> responsibils = recordService.recordRespon(id);
        model.addAttribute("responsibils", responsibils);

        List<TeamDto> teams = teamService.findTeamList();
        model.addAttribute("teams", teams);

        return "record/recordreg";
    }

    // 출동일지 리스트
    @RequestMapping("/list")
    public String recordList(HttpServletRequest request,Model model){
        HttpSession session = request.getSession();
        if(session.getAttribute("role") == "ROLE_ADMIN" || session.getAttribute("role") == "ROLE_USER") {
            String currentuserid = CommonUtils.getCurrentuser(request);
            Optional<Account> account = accountService.findByUserid(currentuserid);
            String userid = account.get().getUserid();
            model.addAttribute("userid", userid);
            model.addAttribute("role", request.getSession().getAttribute("role"));
            return "record/recordlist";
        }
        return "record/recordlist";
    }

    // 뷰페이지
    @RequestMapping("view/{id}")
    public String recordView(HttpServletRequest request,Model model, @PathVariable Long id){
        String currentuserid = CommonUtils.getCurrentuser(request);
        Optional<Account> account = accountService.findByUserid(currentuserid);
        String userid = account.get().getUserid();
        model.addAttribute("userid", userid);

        //데이터 가져오기
        RecordViewDto recordViewDto = recordService.findByIdView(id);
//        log.info("recordViewDto : "+recordViewDto);
        model.addAttribute("record", recordViewDto);

        List<Responsibil> responsibils = recordService.recordRespon(id);
        model.addAttribute("responsibils", responsibils);

        Optional<Record> record = recordService.findByIdRecord(id);
        if(record.isPresent()) {
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
        }

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

    // 파일다운로드 컨트롤러
    @Value("${base.securityfiles.directory}")
    private String securityfile;
    private static final String APPLICATION_PDF = "application/pdf";

//    // 다운로드
//    @RequestMapping(value ="/GuideLine", method = RequestMethod.GET, produces = APPLICATION_PDF)
//    public @ResponseBody void guideLine(HttpServletResponse response) throws IOException {
//        File file = getFile();
//        InputStream in = new FileInputStream(file);
//        response.setContentType(APPLICATION_PDF);
//        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
//        response.setHeader("Content-Length", String.valueOf(file.length()));
//        FileCopyUtils.copy(in, response.getOutputStream());
//    }

    // 바로링크
    @RequestMapping(value = "/GuideLine", method = RequestMethod.GET, produces = APPLICATION_PDF)
    public @ResponseBody HttpEntity<byte[]> guideLine() throws IOException {
        File file = getFile();
        byte[] document = FileCopyUtils.copyToByteArray(file);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "pdf"));
        header.set("Content-Disposition", "inline; filename=" + file.getName());
        header.setContentLength(document.length);
        return new HttpEntity<byte[]>(document, header);
    }

    private File getFile() throws FileNotFoundException {
        String FILE_PATH= securityfile+"GuideLine.pdf";
        File file = new File(FILE_PATH);
        //파일이 틀렸으면
        if (!file.exists()){
            throw new FileNotFoundException("file with path: " + FILE_PATH + " was not found.");
        }
        return file;
    }

    //모바일갤러리용 페이지
    @RequestMapping("gallery")
    public String gallery(){
        return "record/gallery";
    }

    // 파일다운로드 컨트롤러
    @RequestMapping("mobileDownload/{fileid}")
    @ResponseBody
    public byte[] downProcess2(HttpServletResponse response,
                              @PathVariable Long fileid) throws IOException {

        Optional<MobileUploadFile> optionalUploadFile = mobileImageService.findById(fileid);
        String filePath = optionalUploadFile.get().getAfmFilePath();
        String filename = URLEncoder.encode(optionalUploadFile.get().getAfmFilename(),"UTF-8").replaceAll("\\+", "%20");
        File file = new File(filePath);
        byte[] bytes = FileCopyUtils.copyToByteArray(file);

        response.setHeader("Content-Disposition",
                "attachment;filename=\"" + filename + "\"");
        response.setContentLength(bytes.length);
        return bytes;
    }

}
