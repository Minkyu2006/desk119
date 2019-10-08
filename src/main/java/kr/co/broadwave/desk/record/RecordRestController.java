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
import kr.co.broadwave.desk.notice.file.UploadFile;
import kr.co.broadwave.desk.record.file.RecordImageService;
import kr.co.broadwave.desk.record.responsibil.Responsibil;
import kr.co.broadwave.desk.teams.Team;
import kr.co.broadwave.desk.teams.TeamDto;
import kr.co.broadwave.desk.teams.TeamService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

import kr.co.broadwave.desk.record.file.RecordUploadFile;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Repeatable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author MinKyu
 * Date : 2019-09-05
 * Remark : 출동일지 Rest컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/record")
public class RecordRestController {
    private AjaxResponse res = new AjaxResponse();
    HashMap<String, Object> data = new HashMap<>();

    private final RecordService recordService;
    private final AccountService accountService;
    private final MasterCodeService masterCodeService;
    private final ModelMapper modelMapper;
    private final RecordRepository recordRepository;
    private final RecordImageService recordImageService;
    private final MailService mailService;
    private final TeamService teamService;

    @Autowired
    public RecordRestController(RecordService recordService,
                                AccountService accountService,
                                ModelMapper modelMapper,
                                RecordRepository recordRepository,
                                RecordImageService recordImageService,
                                MailService mailService,
                                TeamService teamService,
                                MasterCodeService masterCodeService) {
        this.recordService = recordService;
        this.accountService = accountService;
        this.modelMapper = modelMapper;
        this.recordRepository = recordRepository;
        this.masterCodeService = masterCodeService;
        this.recordImageService = recordImageService;
        this.mailService = mailService;
        this.teamService = teamService;
    }

    // 출동일지작성 저장기능
    @PostMapping("reg")
    public ResponseEntity recordSave(@ModelAttribute RecordMapperDto recordMapperDto,
                                     MultipartHttpServletRequest multi,
                                     HttpServletRequest request) throws Exception {

        Record record = modelMapper.map(recordMapperDto,Record.class);

        // State값 넣기(제출완료 : 1 , 임시저장 : 0)
        record.setArRecordState(1);

        // 이메일전송
        List<MasterCodeDto> mailListLRaws = masterCodeService.findCodeList(CodeType.C0003);
        List<String> maillists = new ArrayList<>();

        for (MasterCodeDto masterCodeDto : mailListLRaws) {
            maillists.add(masterCodeDto.getName());
        }

        mailService.mailsend(maillists,
                "출동일지 <"+record.getArNumber() + "> 가 등록(제출완료) 되었습니다",
                "작성자 : " + record.getArWriter()+"\r\n",
                "출동일지제목 : "+record.getArTitle()+"\r\n",
                " 조사일자 : "+record.getArIntoStart()+" ~ ",record.getArIntoEnd());

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

        //파일저장
        Iterator<String> files = multi.getFileNames();
        System.out.println("files :"+files);
        while(files.hasNext()) {
            String recorduploadFile = files.next();
            MultipartFile mFile = multi.getFile(recorduploadFile);
            String fileName = mFile.getOriginalFilename();
            //파일이 존재할때만
            if (!mFile.isEmpty()) {
                //System.out.println("파일명 확인  : " + fileName);
                recordImageService.store(mFile,recordSave);
                //파일명 순번 채번하기 , 코멘트추가
                recordImageService.makefileseq(recordSave);
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

        log.info("출동일지 저장 성공 : " + recordSave.toString() );
        log.info("조사담당자 저장 성공 : " + responsibils.toString() );
        return ResponseEntity.ok(res.success());
    }

    // 출동일지작성 임시저장기능
    @PostMapping("temreg")
    public ResponseEntity recordTemSave(@ModelAttribute RecordMapperDto recordMapperDto,
                                     MultipartHttpServletRequest multi,
                                     HttpServletRequest request) throws Exception {
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

        //파일저장
        Iterator<String> files = multi.getFileNames();
        System.out.println("files :"+files);
        while(files.hasNext()) {
            String recorduploadFile = files.next();
            MultipartFile mFile = multi.getFile(recorduploadFile);
            String fileName = mFile.getOriginalFilename();
            //파일이 존재할때만
            if (!mFile.isEmpty()) {
                //System.out.println("파일명 확인  : " + fileName);
                recordImageService.store(mFile,recordSave);
                //파일명 순번 채번하기
                recordImageService.makefileseq(recordSave);
            }
        }

        //조사담당자
        List<Responsibil> responsibil = new ArrayList<>();

        String[] arEmployeeNumber = request.getParameterValues("arEmployeeNumber");
        String[] arEmployeeName = request.getParameterValues("arEmployeeName");
        String[] teamcode = request.getParameterValues("teamcode");

        for (int i = 0; i < arEmployeeNumber.length; i++) {
            Optional<Team> byTeamcode = teamService.findByTeamcode(teamcode[i]);
            if (!byTeamcode.isPresent()){
                Responsibil responsibils = Responsibil.builder()
                        .record(recordSave)
                        .arEmployeeNumber(arEmployeeNumber[i])
                        .arEmployeeName(arEmployeeName[i])
                        .team(byTeamcode.get())
                        .build();
                if ( !arEmployeeNumber[i].isEmpty() || !arEmployeeName[i].isEmpty()){
                    responsibil.add(responsibils);
                }
            }
        }

        recordService.recordResponSave(responsibil);

        log.info("출동일지 임시저장 성공 : " + recordSave.toString() );
        return ResponseEntity.ok(res.success());
    }

    // 행정구역 도시선택시 -> 부도시select기능
    @PostMapping("location")
    public ResponseEntity Location(
            @RequestParam(value="locationCityType", defaultValue="") String locationCityType){

        List<CommonCode> commonCodes = new ArrayList<>();

        Arrays.stream(LocationAddressType.values())
                .filter(v -> v.getLocationCityType().equals(locationCityType))
                .forEach(v->{
                    CommonCode commonCode = new CommonCode(v.getCode(),v.getDesc());
                    commonCodes.add(commonCode);
                });
        data.clear();
        data.put("dataselect",commonCodes);

        res.addResponse("data",data);
        return ResponseEntity.ok(res.success());
    }

    // 출동일지 조회
    @PostMapping("list")
    public ResponseEntity recordList(@RequestParam(value="arTitle", defaultValue="") String arTitle,
                                     @RequestParam(value="arWriter", defaultValue="") String arWriter,
                                     @RequestParam(value="arNumber", defaultValue="") String arNumber,
                                     Pageable pageable){

        log.info("출동일지 조회 / 조회조건 : arTitle / '" + arTitle + "' arWriter / '" + arWriter + "'"+ "' arNumber / '" + arNumber + "'");

        Page<RecrodListDto> records = recordService.findAllBySearchStrings(arNumber, arTitle, arWriter, pageable);
        return CommonUtils.ResponseEntityPage(records);
    }

    // 출동일지 삭제
    @PostMapping("del")
    public ResponseEntity recordDelete(@RequestParam(value="recordid", defaultValue="") Long recordid){

        log.info("출동일지 삭제 시작 / 고유번호 ID : '" + recordid + "'");

        Optional<Record> optionalRecord = recordService.findByIdRecord(recordid);

        if (!optionalRecord.isPresent()){
            log.info("출동일지 삭제 에러 데이터존재하지않습니다.('E003') fileID: '" + recordid + "'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E003.getCode(), ResponseErrorCode.E003.getDesc()));
        }
        recordService.delete(optionalRecord.get());
        log.info("출동일지 삭제 성공 / 고유번호 ID : '" + recordid + "'");
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
            RecordUploadFile uploadedFile = recordImageService.store(file,null);
            return ResponseEntity.ok().body("/api/record/image/" + uploadedFile.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    //출동일지 파일삭제
    @PostMapping("filedel")
    public ResponseEntity filedelete(@RequestParam(value="fileid", defaultValue="") Long fileid){
        log.info("출동일지 첨부파일삭제 시작/ 파일ID : '" + fileid + "'");
        int resultValue = recordImageService.recorduploadFileDelete(fileid);
        if (resultValue == -1){
            log.info("출동일지 삭제시 에러발생('E015) fileID: '" + fileid + "'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E015.getCode(), ResponseErrorCode.E015.getDesc()));
        }
        log.info("출동일지 첨부파일삭제 성공/ 파일ID : '" + fileid + "'");
        return ResponseEntity.ok(res.success());
    }

    //조사담당자 삭제
    @PostMapping("respondel")
    public ResponseEntity respondelete(@RequestParam(value="rsid", defaultValue="") Long rsid){
        log.info("조사담당자 삭제 시작: '" + rsid + "'");
        int resultValue = recordService.recordresponsibilDelete(rsid);
        if (resultValue == -1){
            log.info("조사담당자 삭제시 에러발생('E015) fileID: '" + rsid + "'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E017.getCode(), ResponseErrorCode.E017.getDesc()));
        }
        log.info("조사담당자삭제 성공 : '" + rsid + "'");
        return ResponseEntity.ok(res.success());
    }

    // 조사담당자 select 추가
    @PostMapping ("recordteam")
    public ResponseEntity recordteam(){
        List<TeamDto> teams = teamService.findTeamList();
        data.clear();
        data.put("teamdata",teams);
        res.addResponse("data",data);
        return ResponseEntity.ok(res.success());
    }



















}
