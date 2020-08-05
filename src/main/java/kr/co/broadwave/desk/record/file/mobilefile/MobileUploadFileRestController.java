package kr.co.broadwave.desk.record.file.mobilefile;

import kr.co.broadwave.desk.accounts.Account;
import kr.co.broadwave.desk.accounts.AccountService;
import kr.co.broadwave.desk.common.AjaxResponse;
import kr.co.broadwave.desk.common.CommonUtils;
import kr.co.broadwave.desk.common.ResponseErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

/**
 * @author MinKyu
 * Date : 2019-09-05
 * Remark : 출동일지 Rest컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/mobile")
public class MobileUploadFileRestController {

    private final AccountService accountService;
    private final MobileImageService mobileImageService;
    @Autowired
    public MobileUploadFileRestController(MobileImageService mobileImageService,
                                          AccountService accountService) {
        this.mobileImageService = mobileImageService;
        this.accountService = accountService;
    }

    // 출동일지작성 저장기능
    @PostMapping("upload")
    public ResponseEntity<Map<String,Object>> recordSave(MultipartHttpServletRequest multi,
                                                         HttpServletRequest request) throws Exception {

        AjaxResponse res = new AjaxResponse();

        String currentuserid = CommonUtils.getCurrentuser(request);
        Optional<Account> optionalAccount = accountService.findByUserid(currentuserid);

        //아이디가 존재하지않으면
        if (!optionalAccount.isPresent()) {
            log.info("출동일지 저장한 사람 아이디 미존재 : '" + currentuserid + "'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E014.getCode(),
                    ResponseErrorCode.E014.getDesc() + "'" + currentuserid + "'" ));
        }

        // 인데스 사진 파일저장
        Iterator<String> files = multi.getFileNames();
        while(files.hasNext()) {
            String mobileuploadFile = files.next();
            MultipartFile mFile = multi.getFile(mobileuploadFile);
            assert mFile != null;

            if (!mFile.isEmpty()) {
                mobileImageService.mobileUploadFile(mFile,optionalAccount.get());
            }
        }

        return ResponseEntity.ok(res.success());
    }

//    // 출동일지작성 임시저장기능
//    @PostMapping("temreg")
//    public ResponseEntity<Map<String,Object>> recordTemSave(@ModelAttribute RecordMapperDto recordMapperDto,
//                                     MultipartHttpServletRequest multi,
//                                     HttpServletRequest request) throws Exception {
//        AjaxResponse res = new AjaxResponse();
//
//        String currentuserid = CommonUtils.getCurrentuser(request);
//
//        Record record = modelMapper.map(recordMapperDto,Record.class);
//
//        record.setArRecordState(0);
//        Optional<MasterCode> optionalRelatedId = masterCodeService.findById(recordMapperDto.getArRelatedId());
//        Optional<Account> optionalAccount = accountService.findByUserid(currentuserid);
//        Optional<Record> optionalRecord = recordRepository.findByArNumber(record.getArNumber());
//
//        //아이디가 존재하지않으면
//        if (!optionalAccount.isPresent()) {
//            log.info("출동일지 저장한 사람 아이디 미존재 : '" + currentuserid + "'");
//            return ResponseEntity.ok(res.fail(ResponseErrorCode.E014.getCode(),
//                    ResponseErrorCode.E014.getDesc() + "'" + currentuserid + "'" ));
//        }
//
//        //관련부처가 존재하지않으면
//        if (!optionalRelatedId.isPresent()) {
//            log.info(" 선택한 직급 DB 존재 여부 체크.  직급코드: '" + recordMapperDto.getArRelatedId() +"'");
//            return ResponseEntity.ok(res.fail(ResponseErrorCode.E016.getCode(),
//                    ResponseErrorCode.E016.getDesc()));
//        }else{
//            record.setArRelatedId(optionalRelatedId.get());
//        }
//
//        //신규 및 업데이트여부
//        if (optionalRecord.isPresent()){
//            //수정
//            record.setId(optionalRecord.get().getId());
//            record.setInsert_id(optionalRecord.get().getInsert_id());
//            record.setInsert_name(optionalRecord.get().getInsert_name());
//            record.setInsertDateTime(optionalRecord.get().getInsertDateTime());
//
//            record.setModify_id(currentuserid);
//            record.setModify_name(optionalAccount.get().getUsername());
//            record.setModifyDateTime(LocalDateTime.now());
//        }else{
//            //신규
//            record.setInsert_id(currentuserid);
//            record.setInsert_name(optionalAccount.get().getUsername());
//            record.setInsertDateTime(LocalDateTime.now());
//            record.setModify_id(currentuserid);
//            record.setModify_name(optionalAccount.get().getUsername());
//            record.setModifyDateTime(LocalDateTime.now());
//        }
//
//        Record recordSave = recordService.save(record);
//
//        String filecomment = recordMapperDto.getArComment();
//        String[] filecommentList = filecomment.split(",");
//        int j =0;
//        //파일저장
//        Iterator<String> files = multi.getFileNames();
//        while(files.hasNext()) {
//            String recorduploadFile = files.next();
//            MultipartFile mFile = multi.getFile(recorduploadFile);
////            String fileName = mFile.getOriginalFilename();
//            //파일이 존재할때만
//            if (!mFile.isEmpty()) {
//                //System.out.println("파일명 확인  : " + fileName);
//                recordImageService.store(mFile,recordSave,filecommentList[j]);
//                j++;
//            }
//        }
//
//        //파일명 순번 채번하기
////        recordImageService.makefilenew(recordSave,j);
//
//        //조사담당자
//        List<Responsibil> responsibils = new ArrayList<>();
//
//        String[] arEmployeeNumber = request.getParameterValues("arEmployeeNumber");
//        String[] arEmployeeName = request.getParameterValues("arEmployeeName");
//        String[] teamcode = request.getParameterValues("teamcode");
//
//        for (int i = 0; i < arEmployeeNumber.length; i++) {
//            Optional<Team> byTeamcode = teamService.findByTeamcode(teamcode[i]);
//            if (byTeamcode.isPresent()){
//                Responsibil responsibils2 = Responsibil.builder()
//                        .record(recordSave)
//                        .arEmployeeNumber(arEmployeeNumber[i])
//                        .arEmployeeName(arEmployeeName[i])
//                        .team(byTeamcode.get())
//                        .build();
//                if ( !arEmployeeNumber[i].isEmpty() || !arEmployeeName[i].isEmpty()){
//                    responsibils.add(responsibils2);
//                }
//            }
//        }
//
//        recordService.recordResponSave(responsibils);
//
////        log.info("출동일지 임시저장 성공 : " + recordSave.toString() );
//        return ResponseEntity.ok(res.success());
//    }
//
//    // 행정구역 도시선택시 -> 부도시select기능
//    @PostMapping("location")
//    public ResponseEntity<Map<String,Object>> Location(
//            @RequestParam(value="locationCityType", defaultValue="") String locationCityType){
//        AjaxResponse res = new AjaxResponse();
//        HashMap<String, Object> data = new HashMap<>();
//
//        List<CommonCode> commonCodes = new ArrayList<>();
//
//        Arrays.stream(LocationAddressType.values())
//                .filter(v -> v.getLocationCityType().equals(locationCityType))
//                .forEach(v->{
//                    CommonCode commonCode = new CommonCode(v.getCode(),v.getDesc());
//                    commonCodes.add(commonCode);
//                });
//
//        data.put("dataselect",commonCodes);
//
//        res.addResponse("data",data);
//        return ResponseEntity.ok(res.success());
//    }
//
//    // 출동일지 조회
//    @PostMapping("list")
//    public ResponseEntity<Map<String,Object>> recordList(@RequestParam(value="arTitle", defaultValue="") String arTitle,
//                                     @RequestParam(value="arWriter", defaultValue="") String arWriter,
//                                     @RequestParam(value="arNumber", defaultValue="") String arNumber,
//                                     Pageable pageable){
//
////        log.info("출동일지 조회 / 조회조건 : arTitle / '" + arTitle + "' arWriter / '" + arWriter + "'"+ "' arNumber / '" + arNumber + "'");
//
//        Page<RecrodListDto> records = recordService.findAllBySearchStrings(arNumber, arTitle, arWriter, pageable);
//        return CommonUtils.ResponseEntityPage(records);
//    }
//
//    // 출동일지 삭제
//    @PostMapping("del")
//    public ResponseEntity<Map<String,Object>> recordDelete(@RequestParam(value="recordid", defaultValue="") Long recordid){
//
//        AjaxResponse res = new AjaxResponse();
//
////        log.info("출동일지 삭제 시작 / 고유번호 ID : '" + recordid + "'");
//
//        Optional<Record> optionalRecord = recordService.findByIdRecord(recordid);
////        List<Responsibil> optionalResponsibil = recordService.recordRespon(recordid);
//
//        if (!optionalRecord.isPresent()){
//            log.info("출동일지 삭제 에러 데이터존재하지않습니다.('E003') fileID: '" + recordid + "'");
//            return ResponseEntity.ok(res.fail(ResponseErrorCode.E003.getCode(), ResponseErrorCode.E003.getDesc()));
//        }
//
//        recordService.delete(optionalRecord.get());
//        recordImageService.fileDel(optionalRecord.get());
//
////        log.info("출동일지 삭제 성공 / 고유번호 ID : '" + recordid + "'");
//
//        return ResponseEntity.ok(res.success());
//    }
//
//    @GetMapping("/image/{fileId}")
//    @ResponseBody
//    public ResponseEntity<?> serveFile(@PathVariable Long fileId) {
//        try {
//            RecordUploadFile recorduploadedFile = recordImageService.load(fileId);
//            HttpHeaders headers = new HttpHeaders();
//            String fileName = recorduploadedFile.getAfFileName();
//            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");
//            if (MediaUtils.containsImageMediaType(recorduploadedFile.getContentType())) {
//                headers.setContentType(MediaType.valueOf(recorduploadedFile.getContentType()));
//            } else {
//                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//            }
//            Resource resource = recordImageService.loadAsResource(recorduploadedFile.getAfSaveFileName());
//            return ResponseEntity.ok().headers(headers).body(resource);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.badRequest().build();
//        }
//    }
//
//    // 이미지등록
//    @PostMapping("image")
//    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
//        try {
//            RecordUploadFile uploadedFile = recordImageService.store(file,null,null);
//            return ResponseEntity.ok().body("/api/record/image/" + uploadedFile.getId());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.badRequest().build();
//        }
//    }
//
//    //출동일지 파일삭제
//    @PostMapping("filedel")
//    public ResponseEntity<Map<String,Object>> filedel(@RequestParam(value="fileid", defaultValue="") Long fileid){
//
//        AjaxResponse res = new AjaxResponse();
//
////        log.info("출동일지 첨부파일삭제 시작/ 파일ID : '" + fileid + "'");
//        int resultValue = recordImageService.recorduploadFileDelete(fileid);
//        if (resultValue == -1){
////            log.info("출동일지 삭제시 에러발생('E015) fileID: '" + fileid + "'");
//            return ResponseEntity.ok(res.fail(ResponseErrorCode.E015.getCode(), ResponseErrorCode.E015.getDesc()));
//        }
////        log.info("출동일지 첨부파일삭제 성공/ 파일ID : '" + fileid + "'");
//        return ResponseEntity.ok(res.success());
//    }
//
//    //조사담당자 삭제
//    @PostMapping("respondel")
//    public ResponseEntity<Map<String,Object>> respondelete(@RequestParam(value="rsid", defaultValue="") Long rsid){
//
//        AjaxResponse res = new AjaxResponse();
//
////        log.info("조사담당자 삭제 시작: '" + rsid + "'");
//        int resultValue = recordService.recordresponsibilDelete(rsid);
//        if (resultValue == -1){
////            log.info("조사담당자 삭제시 에러발생('E015) fileID: '" + rsid + "'");
//            return ResponseEntity.ok(res.fail(ResponseErrorCode.E017.getCode(), ResponseErrorCode.E017.getDesc()));
//        }
////        log.info("조사담당자삭제 성공 : '" + rsid + "'");
//        return ResponseEntity.ok(res.success());
//    }
//
//    // 조사담당자 select 추가
//    @PostMapping ("recordteam")
//    public ResponseEntity<Map<String,Object>> recordteam(){
//        AjaxResponse res = new AjaxResponse();
//        HashMap<String, Object> data = new HashMap<>();
//
//        List<TeamDto> teams = teamService.findTeamList();
//
//        data.put("teamdata",teams);
//        res.addResponse("data",data);
//        return ResponseEntity.ok(res.success());
//    }



}
