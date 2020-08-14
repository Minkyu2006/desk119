package kr.co.broadwave.desk.record;

import kr.co.broadwave.desk.accounts.Account;
import kr.co.broadwave.desk.accounts.AccountService;
import kr.co.broadwave.desk.bscodes.CodeType;
import kr.co.broadwave.desk.bscodes.CommonCode;
import kr.co.broadwave.desk.bscodes.LocationAddressType;
import kr.co.broadwave.desk.common.AjaxResponse;
import kr.co.broadwave.desk.common.CommonUtils;
import kr.co.broadwave.desk.common.MediaUtils;
import kr.co.broadwave.desk.common.ResponseErrorCode;
import kr.co.broadwave.desk.mail.MailService;
import kr.co.broadwave.desk.mastercode.MasterCode;
import kr.co.broadwave.desk.mastercode.MasterCodeDto;
import kr.co.broadwave.desk.mastercode.MasterCodeService;
import kr.co.broadwave.desk.record.file.RecordImageService;
import kr.co.broadwave.desk.record.file.RecordUploadFile;
import kr.co.broadwave.desk.record.file.RecordUploadFileRepository;
import kr.co.broadwave.desk.record.file.mobilefile.MobileImageService;
import kr.co.broadwave.desk.record.file.mobilefile.MobileUploadFile;
import kr.co.broadwave.desk.record.responsibil.Responsibil;
import kr.co.broadwave.desk.teams.Team;
import kr.co.broadwave.desk.teams.TeamDto;
import kr.co.broadwave.desk.teams.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author MinKyu
 * Date : 2019-09-05
 * Remark : 출동일지 Rest컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/record")
public class RecordRestController {

    private final RecordService recordService;
    private final AccountService accountService;
    private final MasterCodeService masterCodeService;
    private final ModelMapper modelMapper;
    private final RecordRepository recordRepository;
    private final RecordImageService recordImageService;
    private final MailService mailService;
    private final TeamService teamService;
    private final MobileImageService mobileImageService;
    private final RecordUploadFileRepository recordUploadFileRepository;
    @Autowired
    public RecordRestController(RecordService recordService,
                                AccountService accountService,
                                ModelMapper modelMapper,
                                RecordRepository recordRepository,
                                RecordImageService recordImageService,
                                MailService mailService,
                                TeamService teamService,
                                MasterCodeService masterCodeService,
                                RecordUploadFileRepository recordUploadFileRepository,
                                MobileImageService mobileImageService) {
        this.recordService = recordService;
        this.accountService = accountService;
        this.modelMapper = modelMapper;
        this.recordRepository = recordRepository;
        this.masterCodeService = masterCodeService;
        this.recordImageService = recordImageService;
        this.mailService = mailService;
        this.teamService = teamService;
        this.mobileImageService = mobileImageService;
        this.recordUploadFileRepository = recordUploadFileRepository;
    }

    // 출동일지작성 저장기능
    @PostMapping("reg")
    public ResponseEntity<Map<String,Object>> recordSave(@ModelAttribute RecordMapperDto recordMapperDto,
                                     MultipartHttpServletRequest multi,
                                     HttpServletRequest request) throws Exception {

        AjaxResponse res = new AjaxResponse();

        Record record = modelMapper.map(recordMapperDto,Record.class);

        // State값 넣기(제출완료 : 1 , 임시저장 : 0)
        record.setArRecordState(1);

        Optional<MasterCode> optionalRelatedId = masterCodeService.findById(recordMapperDto.getArRelatedId());

        String currentuserid = CommonUtils.getCurrentuser(request);
        Optional<Account> optionalAccount = accountService.findByUserid(currentuserid);

        Optional<Record> optionalRecord = recordRepository.findByArNumber(record.getArNumber());

        //아이디가 존재하지않으면
        if (!optionalAccount.isPresent()) {
            log.info("출동일지 저장한 사람 아이디 미존재 : '" + currentuserid + "'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E014.getCode(),
                    ResponseErrorCode.E014.getDesc() + "'" + currentuserid + "'" ));
        }
        //관련부처가 존재하지않으면
        if (!optionalRelatedId.isPresent()) {
            log.info(" 선택한 직급 DB 존재 여부 체크.  직급코드: '" + recordMapperDto.getArRelatedId() +"'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E016.getCode(),
                    ResponseErrorCode.E016.getDesc()));
        }else{
            record.setArRelatedId(optionalRelatedId.get());
        }
        //신규 및 업데이트여부
        if (optionalRecord.isPresent()){
            //수정
            record.setId(optionalRecord.get().getId());
            record.setInsert_id(optionalRecord.get().getInsert_id());
            record.setInsert_name(optionalRecord.get().getInsert_name());
            record.setInsertDateTime(optionalRecord.get().getInsertDateTime());
            record.setModify_id(currentuserid);
            record.setModify_name(optionalAccount.get().getUsername());
            record.setModifyDateTime(LocalDateTime.now());
        }else{
            //신규
            record.setInsert_id(currentuserid);
            record.setInsert_name(optionalAccount.get().getUsername());
            record.setInsertDateTime(LocalDateTime.now());
            record.setModify_id(currentuserid);
            record.setModify_name(optionalAccount.get().getUsername());
            record.setModifyDateTime(LocalDateTime.now());
        }

        Record recordSave = recordService.save(record);

        int y = 0;
        int x = Integer.parseInt(recordMapperDto.getFileValue());
//        log.info("x : "+x);
        if(recordMapperDto.getOutlineFileName()!=null){
            Optional<MobileUploadFile> optionalMobileUploadFile = mobileImageService.findById(recordMapperDto.getOutlineFileName());
            if(optionalMobileUploadFile.isPresent()) {
                recordImageService.store3(optionalMobileUploadFile.get(), recordSave, 1,null);
                y++;
                x++;
            }
        }
        if(recordMapperDto.getResultFileName()!=null){
            Optional<MobileUploadFile> optionalMobileUploadFile = mobileImageService.findById(recordMapperDto.getResultFileName());
            if(optionalMobileUploadFile.isPresent()) {
                recordImageService.store3(optionalMobileUploadFile.get(), recordSave, 2,null);
                y++;
                x++;
            }
        }
        if(recordMapperDto.getOpinionFileName()!=null){
            Optional<MobileUploadFile> optionalMobileUploadFile = mobileImageService.findById(recordMapperDto.getOpinionFileName());
            if(optionalMobileUploadFile.isPresent()) {
                recordImageService.store3(optionalMobileUploadFile.get(), recordSave, 3,null);
                y++;
                x++;
            }
        }

        int j = 0;
        int b = 0;
        //파일저장
        Iterator<String> files = multi.getFileNames();
        List<Long> dbIds = recordMapperDto.getFilenamedbIds();
        List<String> filecomment = recordMapperDto.getArComments();
//        log.info("dbIds : "+dbIds);
//        log.info("dbIds Size : "+dbIds.size());
//        log.info("filecomment : "+filecomment);
//        log.info("filecomment Size : "+filecomment.size());
        while(files.hasNext()) {
            String recorduploadFile = files.next();
            MultipartFile mFile = multi.getFile(recorduploadFile);
            assert mFile != null;
            String a = mFile.getName();
            String comment = "설명없음";
//            log.info("filecomment : "+filecomment);
            if(filecomment.size() != 0) {
                if (filecomment.get(j).equals("")) {
                    comment = "설명없음";
                } else {
                    comment = filecomment.get(j);
                }
            }
//            log.info("a : "+a);
//            log.info("mFile : "+mFile);
            if(y<3) {
                if(a.equals("outlineFile") && recordMapperDto.getOutlineFileName() == null){
                    if (!mFile.isEmpty()) {
                        recordImageService.store2(mFile, recordSave, 1);
                        x++;
                    }
                    y++;
                }else if(a.equals("resultFile") && recordMapperDto.getResultFileName() == null){
                    if (!mFile.isEmpty()) {
                        recordImageService.store2(mFile, recordSave, 2);
                        x++;
                    }
                    y++;
                }else if(a.equals("opinionFile") && recordMapperDto.getOpinionFileName() == null){
                    if (!mFile.isEmpty()) {
                        recordImageService.store2(mFile, recordSave, 3);
                        x++;
                    }
                    y++;
                }
            }else {
                //파일이 존재할때만
                if (!mFile.isEmpty()) {
                    recordImageService.store(mFile, recordSave, comment);
                    dbIds.remove(0);
                    j++;
                    //파일명 순번 채번하기 (기타사진전용)
                    recordImageService.makefilenew(recordSave, x);
                }else {
                    if(dbIds.size() != 0){
                        if(dbIds.get(b) != null) {
                            Optional<MobileUploadFile> optionalMobileUploadFile = mobileImageService.findById(dbIds.get(b));
                            if (optionalMobileUploadFile.isPresent()) {
                                recordImageService.store3(optionalMobileUploadFile.get(), recordSave, 0, comment);
                                recordImageService.makefilenew(recordSave, x);
                                b++;
                                j++;
                            }
                        }
                    }
                }
            }
        }

        // 이메일전송
        if (!optionalRecord.isPresent()) {
            List<MasterCodeDto> mailListLRaws = masterCodeService.findCodeList(CodeType.C0003);
            List<String> maillists = new ArrayList<>();

            for (MasterCodeDto masterCodeDto : mailListLRaws) {
                maillists.add(masterCodeDto.getName());
            }

            if(maillists.size() != 0) {
                mailService.mailsend(maillists,
                        "<" + record.getArNumber() + "> 출동일지가 등록 되었습니다",
                        "작성자 : " + record.getArWriter() + "\r\n\n",
                        "출동일지제목 : " + record.getArTitle() + "\r\n\n",
                        " 조사일자 : " + record.getArIntoStart() + " ~ ", record.getArIntoEnd() + "\r\n\n",
                        "해당글 보러가기 : https://kict119.broadwave.co.kr/record/view/" + record.getId());
            }
        }

        //조사담당자
        List<Responsibil> responsibils = new ArrayList<>();

        String[] arEmployeeNumber = request.getParameterValues("arEmployeeNumber");
        String[] arEmployeeName = request.getParameterValues("arEmployeeName");
        String[] teamcode = request.getParameterValues("teamcode");

        for (int i = 0; i < arEmployeeNumber.length; i++) {
            Optional<Team> byTeamcode = teamService.findByTeamcode(teamcode[i]);
            if (byTeamcode.isPresent()){
                Responsibil responsibils2 = Responsibil.builder()
                        .record(recordSave)
                        .arEmployeeNumber(arEmployeeNumber[i])
                        .arEmployeeName(arEmployeeName[i])
                        .team(byTeamcode.get())
                        .build();
                if ( !arEmployeeNumber[i].isEmpty() || !arEmployeeName[i].isEmpty()){
                    responsibils.add(responsibils2);
                }
            }
        }

        recordService.recordResponSave(responsibils);

//        log.info("출동일지 저장 성공 : " + recordSave.toString() );
//        log.info("조사담당자 저장 성공 : " + responsibils.toString() );
        return ResponseEntity.ok(res.success());
    }

    // 출동일지작성 임시저장기능
    @PostMapping("temreg")
    public ResponseEntity<Map<String,Object>> recordTemSave(@ModelAttribute RecordMapperDto recordMapperDto,
                                     MultipartHttpServletRequest multi,
                                     HttpServletRequest request) throws Exception {
        AjaxResponse res = new AjaxResponse();

        String currentuserid = CommonUtils.getCurrentuser(request);

        Record record = modelMapper.map(recordMapperDto,Record.class);

        record.setArRecordState(0);
        Optional<MasterCode> optionalRelatedId = masterCodeService.findById(recordMapperDto.getArRelatedId());
        Optional<Account> optionalAccount = accountService.findByUserid(currentuserid);
        Optional<Record> optionalRecord = recordRepository.findByArNumber(record.getArNumber());

        //아이디가 존재하지않으면
        if (!optionalAccount.isPresent()) {
            log.info("출동일지 저장한 사람 아이디 미존재 : '" + currentuserid + "'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E014.getCode(),
                    ResponseErrorCode.E014.getDesc() + "'" + currentuserid + "'" ));
        }

        //관련부처가 존재하지않으면
        if (!optionalRelatedId.isPresent()) {
            log.info(" 선택한 직급 DB 존재 여부 체크.  직급코드: '" + recordMapperDto.getArRelatedId() +"'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E016.getCode(),
                    ResponseErrorCode.E016.getDesc()));
        }else{
            record.setArRelatedId(optionalRelatedId.get());
        }

        //신규 및 업데이트여부
        if (optionalRecord.isPresent()){
            //수정
            record.setId(optionalRecord.get().getId());
            record.setInsert_id(optionalRecord.get().getInsert_id());
            record.setInsert_name(optionalRecord.get().getInsert_name());
            record.setInsertDateTime(optionalRecord.get().getInsertDateTime());

            record.setModify_id(currentuserid);
            record.setModify_name(optionalAccount.get().getUsername());
            record.setModifyDateTime(LocalDateTime.now());
        }else{
            //신규
            record.setInsert_id(currentuserid);
            record.setInsert_name(optionalAccount.get().getUsername());
            record.setInsertDateTime(LocalDateTime.now());
            record.setModify_id(currentuserid);
            record.setModify_name(optionalAccount.get().getUsername());
            record.setModifyDateTime(LocalDateTime.now());
        }

        Record recordSave = recordService.save(record);

        int y = 0;
        int x = Integer.parseInt(recordMapperDto.getFileValue());
//        log.info("x : "+x);
        if(recordMapperDto.getOutlineFileName()!=null){
            Optional<MobileUploadFile> optionalMobileUploadFile = mobileImageService.findById(recordMapperDto.getOutlineFileName());
            if(optionalMobileUploadFile.isPresent()) {
                recordImageService.store3(optionalMobileUploadFile.get(), recordSave, 1,null);
                y++;
                x++;
            }
        }
        if(recordMapperDto.getResultFileName()!=null){
            Optional<MobileUploadFile> optionalMobileUploadFile = mobileImageService.findById(recordMapperDto.getResultFileName());
            if(optionalMobileUploadFile.isPresent()) {
                recordImageService.store3(optionalMobileUploadFile.get(), recordSave, 2,null);
                y++;
                x++;
            }
        }
        if(recordMapperDto.getOpinionFileName()!=null){
            Optional<MobileUploadFile> optionalMobileUploadFile = mobileImageService.findById(recordMapperDto.getOpinionFileName());
            if(optionalMobileUploadFile.isPresent()) {
                recordImageService.store3(optionalMobileUploadFile.get(), recordSave, 3,null);
                y++;
                x++;
            }
        }

        int j = 0;
        int b = 0;
        //파일저장
        Iterator<String> files = multi.getFileNames();
        List<Long> dbIds = recordMapperDto.getFilenamedbIds();
        List<String> filecomment = recordMapperDto.getArComments();
//        log.info("dbIds : "+dbIds);
//        log.info("dbIds Size : "+dbIds.size());
//        log.info("filecomment : "+filecomment);
//        log.info("filecomment Size : "+filecomment.size());
        while(files.hasNext()) {
            String recorduploadFile = files.next();
            MultipartFile mFile = multi.getFile(recorduploadFile);
            assert mFile != null;
            String a = mFile.getName();
            String comment = "설명없음";
//            log.info("filecomment : "+filecomment);
            if(filecomment.size() != 0) {
                if (filecomment.get(j).equals("")) {
                    comment = "설명없음";
                } else {
                    comment = filecomment.get(j);
                }
            }
//            log.info("a : "+a);
//            log.info("mFile : "+mFile);
            if(y<3) {
                if(a.equals("outlineFile") && recordMapperDto.getOutlineFileName() == null){
                    if (!mFile.isEmpty()) {
                        recordImageService.store2(mFile, recordSave, 1);
                        x++;
                    }
                    y++;
                }else if(a.equals("resultFile") && recordMapperDto.getResultFileName() == null){
                    if (!mFile.isEmpty()) {
                        recordImageService.store2(mFile, recordSave, 2);
                        x++;
                    }
                    y++;
                }else if(a.equals("opinionFile") && recordMapperDto.getOpinionFileName() == null){
                    if (!mFile.isEmpty()) {
                        recordImageService.store2(mFile, recordSave, 3);
                        x++;
                    }
                    y++;
                }
            }else {
                //파일이 존재할때만
                if (!mFile.isEmpty()) {
                    recordImageService.store(mFile, recordSave, comment);
                    dbIds.remove(0);
                    j++;
                    //파일명 순번 채번하기 (기타사진전용)
                    recordImageService.makefilenew(recordSave, x);
                }else {
                    if(dbIds.size() != 0){
                        if(dbIds.get(b) != null) {
                            Optional<MobileUploadFile> optionalMobileUploadFile = mobileImageService.findById(dbIds.get(b));
                            if (optionalMobileUploadFile.isPresent()) {
                                recordImageService.store3(optionalMobileUploadFile.get(), recordSave, 0, comment);
                                recordImageService.makefilenew(recordSave, x);
                                b++;
                                j++;
                            }
                        }
                    }
                }
            }
        }

        //조사담당자
        List<Responsibil> responsibils = new ArrayList<>();

        String[] arEmployeeNumber = request.getParameterValues("arEmployeeNumber");
        String[] arEmployeeName = request.getParameterValues("arEmployeeName");
        String[] teamcode = request.getParameterValues("teamcode");

        for (int i = 0; i < arEmployeeNumber.length; i++) {
            Optional<Team> byTeamcode = teamService.findByTeamcode(teamcode[i]);
            if (byTeamcode.isPresent()){
                Responsibil responsibils2 = Responsibil.builder()
                        .record(recordSave)
                        .arEmployeeNumber(arEmployeeNumber[i])
                        .arEmployeeName(arEmployeeName[i])
                        .team(byTeamcode.get())
                        .build();
                if ( !arEmployeeNumber[i].isEmpty() || !arEmployeeName[i].isEmpty()){
                    responsibils.add(responsibils2);
                }
            }
        }

        recordService.recordResponSave(responsibils);

//        log.info("출동일지 임시저장 성공 : " + recordSave.toString() );
        return ResponseEntity.ok(res.success());
    }

    // 행정구역 도시선택시 -> 부도시select기능
    @PostMapping("location")
    public ResponseEntity<Map<String,Object>> Location(
            @RequestParam(value="locationCityType", defaultValue="") String locationCityType){
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        List<CommonCode> commonCodes = new ArrayList<>();

        Arrays.stream(LocationAddressType.values())
                .filter(v -> v.getLocationCityType().equals(locationCityType))
                .forEach(v->{
                    CommonCode commonCode = new CommonCode(v.getCode(),v.getDesc());
                    commonCodes.add(commonCode);
                });

        data.put("dataselect",commonCodes);

        res.addResponse("data",data);
        return ResponseEntity.ok(res.success());
    }

    // 출동일지 조회
    @PostMapping("list")
    public ResponseEntity<Map<String,Object>> recordList(@RequestParam(value="arTitle", defaultValue="") String arTitle,
                                     @RequestParam(value="arWriter", defaultValue="") String arWriter,
                                     @RequestParam(value="arNumber", defaultValue="") String arNumber,
                                     Pageable pageable){

//        log.info("출동일지 조회 / 조회조건 : arTitle / '" + arTitle + "' arWriter / '" + arWriter + "'"+ "' arNumber / '" + arNumber + "'");

        Page<RecrodListDto> records = recordService.findAllBySearchStrings(arNumber, arTitle, arWriter, pageable);
        return CommonUtils.ResponseEntityPage(records);
    }

    // 출동일지 삭제
    @PostMapping("del")
    public ResponseEntity<Map<String,Object>> recordDelete(@RequestParam(value="recordid", defaultValue="") Long recordid){

        AjaxResponse res = new AjaxResponse();

//        log.info("출동일지 삭제 시작 / 고유번호 ID : '" + recordid + "'");

        Optional<Record> optionalRecord = recordService.findByIdRecord(recordid);
//        List<Responsibil> optionalResponsibil = recordService.recordRespon(recordid);

        if (!optionalRecord.isPresent()){
            log.info("출동일지 삭제 에러 데이터존재하지않습니다.('E003') fileID: '" + recordid + "'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E003.getCode(), ResponseErrorCode.E003.getDesc()));
        }

        recordService.delete(optionalRecord.get());
        recordImageService.fileDel(optionalRecord.get());

//        log.info("출동일지 삭제 성공 / 고유번호 ID : '" + recordid + "'");

        return ResponseEntity.ok(res.success());
    }

    @GetMapping("/image/{fileId}")
    @ResponseBody
    public ResponseEntity<?> serveFile(@PathVariable Long fileId) {
        try {
            RecordUploadFile recorduploadedFile = recordImageService.load(fileId);
            HttpHeaders headers = new HttpHeaders();
            String fileName = recorduploadedFile.getAfFileName();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");
            if (MediaUtils.containsImageMediaType(recorduploadedFile.getContentType())) {
                headers.setContentType(MediaType.valueOf(recorduploadedFile.getContentType()));
            } else {
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            }
            Resource resource = recordImageService.loadAsResource(recorduploadedFile.getAfSaveFileName());
            return ResponseEntity.ok().headers(headers).body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    // 이미지등록
    @PostMapping("image")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            RecordUploadFile uploadedFile = recordImageService.store(file,null,null);
            return ResponseEntity.ok().body("/api/record/image/" + uploadedFile.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }


    //출동일지 파일삭제(DB파일)
    @PostMapping("dbfiledel")
    public ResponseEntity<Map<String,Object>> dbfiledel(@RequestParam(value="fileid", defaultValue="") Long fileid){
        AjaxResponse res = new AjaxResponse();
        Optional<RecordUploadFile> optionalUploadFile = recordUploadFileRepository.findById(fileid);
        optionalUploadFile.ifPresent(recordUploadFileRepository::delete);
        return ResponseEntity.ok(res.success());
    }

    //출동일지 파일삭제(로컬파일)
    @PostMapping("filedel")
    public ResponseEntity<Map<String,Object>> filedel(@RequestParam(value="fileid", defaultValue="") Long fileid){

        AjaxResponse res = new AjaxResponse();

//        log.info("출동일지 첨부파일삭제 시작/ 파일ID : '" + fileid + "'");
        int resultValue = recordImageService.recorduploadFileDelete(fileid);
        if (resultValue == -1){
//            log.info("출동일지 삭제시 에러발생('E015) fileID: '" + fileid + "'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E015.getCode(), ResponseErrorCode.E015.getDesc()));
        }
//        log.info("출동일지 첨부파일삭제 성공/ 파일ID : '" + fileid + "'");
        return ResponseEntity.ok(res.success());
    }

    //조사담당자 삭제
    @PostMapping("respondel")
    public ResponseEntity<Map<String,Object>> respondelete(@RequestParam(value="rsid", defaultValue="") Long rsid){

        AjaxResponse res = new AjaxResponse();

//        log.info("조사담당자 삭제 시작: '" + rsid + "'");
        int resultValue = recordService.recordresponsibilDelete(rsid);
        if (resultValue == -1){
//            log.info("조사담당자 삭제시 에러발생('E015) fileID: '" + rsid + "'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E017.getCode(), ResponseErrorCode.E017.getDesc()));
        }
//        log.info("조사담당자삭제 성공 : '" + rsid + "'");
        return ResponseEntity.ok(res.success());
    }

    // 조사담당자 select 추가
    @PostMapping ("recordteam")
    public ResponseEntity<Map<String,Object>> recordteam(){
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        List<TeamDto> teams = teamService.findTeamList();

        data.put("teamdata",teams);
        res.addResponse("data",data);
        return ResponseEntity.ok(res.success());
    }



















}
