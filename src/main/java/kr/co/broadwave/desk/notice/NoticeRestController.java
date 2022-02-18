package kr.co.broadwave.desk.notice;

import kr.co.broadwave.desk.accounts.Account;
import kr.co.broadwave.desk.accounts.AccountService;
import kr.co.broadwave.desk.bscodes.CodeType;
import kr.co.broadwave.desk.common.AjaxResponse;
import kr.co.broadwave.desk.common.CommonUtils;
import kr.co.broadwave.desk.common.MediaUtils;
import kr.co.broadwave.desk.common.ResponseErrorCode;
import kr.co.broadwave.desk.mail.MailService;
import kr.co.broadwave.desk.mastercode.MasterCodeDto;
import kr.co.broadwave.desk.mastercode.MasterCodeService;
import kr.co.broadwave.desk.notice.file.UploadFile;
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
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author InSeok
 * Date : 2019-07-29
 * Remark :
 */
@Slf4j
@RestController
@RequestMapping("/api/notice")
public class NoticeRestController {
    private AjaxResponse res = new AjaxResponse();
    HashMap<String, Object> data = new HashMap<>();

    private final ModelMapper modelMapper;
    private final NoticeService noticeService;
    private final AccountService accountService;
    private final ImageService imageService;
    private final MailService mailService;
    private final MasterCodeService masterCodeService;

    @Autowired
    public NoticeRestController(ModelMapper modelMapper,
                                NoticeService noticeService,
                                AccountService accountService,
                                ImageService imageService,
                                MailService mailService,
                                MasterCodeService masterCodeService) {
        this.modelMapper = modelMapper;
        this.noticeService = noticeService;
        this.accountService = accountService;
        this.imageService = imageService;
        this.mailService = mailService;
        this.masterCodeService = masterCodeService;
    }

    @GetMapping("/image/{fileId}")
    @ResponseBody
    public ResponseEntity<?> serveFile(@PathVariable Long fileId) {
        try {
            UploadFile uploadedFile = imageService.load(fileId);
            HttpHeaders headers = new HttpHeaders();

            String fileName = uploadedFile.getFileName();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");

            if (MediaUtils.containsImageMediaType(uploadedFile.getContentType())) {
                headers.setContentType(MediaType.valueOf(uploadedFile.getContentType()));
            } else {
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            }

            Resource resource = imageService.loadAsResource(uploadedFile.getSaveFileName());
            return ResponseEntity.ok().headers(headers).body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("image")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            UploadFile uploadedFile = imageService.store(file,null);
            return ResponseEntity.ok().body("/api/notice/image/" + uploadedFile.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }



    @PostMapping("reg")
    public ResponseEntity<Map<String,Object>> noticeSave(MultipartHttpServletRequest multi,HttpServletRequest request) throws Exception {

        String noticeid = multi.getParameter("noticeid");
        String subject = multi.getParameter("subject");
        String content = multi.getParameter("content");
        String bnState = multi.getParameter("bnState");
        Notice notice = Notice.builder()
                .subject(subject)
                .content(content)
                .bnState(Integer.parseInt(bnState))
                .build();


        String currentuserid = CommonUtils.getCurrentuser(request);
        Optional<Account> optionalAccount = accountService.findByUserid(currentuserid);

        if (!optionalAccount.isPresent()) {
            log.info("공지사항 저장한 사람 아이디 미존재('E014) : '" + currentuserid + "'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E014.getCode(), ResponseErrorCode.E014.getDesc() + "'" + currentuserid + "'" ));
        }
        notice.setInsert_id(currentuserid);
        notice.setInsert_name(optionalAccount.get().getUsername());
        notice.setInsertDateTime(LocalDateTime.now());
        notice.setModify_id(currentuserid);
        notice.setModify_name(optionalAccount.get().getUsername());
        notice.setModifyDateTime(LocalDateTime.now());
        notice.setHitCount(0);

        //기존수정이면 저장
        if(!noticeid.isEmpty()){
            notice.setId(Long.parseLong(noticeid));
        }

        Notice noticeSave = noticeService.save(notice);

        if(noticeid.isEmpty()){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            List<MasterCodeDto> mailListLRaws = masterCodeService.findCodeList(CodeType.C0003);
            List<String> maillists = new ArrayList<>();

            for (MasterCodeDto masterCodeDto : mailListLRaws) {
                maillists.add(masterCodeDto.getName());
            }

            if(maillists.size() != 0) {
                mailService.mailsend(maillists,
                        "KICT119 공지사항이 등록 되었습니다",
                        "작성자 : " + notice.getInsert_name() + "\r\n\n",
                        "공지사항 제목 : " + notice.getSubject() + "\r\n\n",
                        " 작성날짜  : " + notice.getInsertDateTime().format(dateTimeFormatter) + "", "" + "\r\n\n",
                        "해당글 보러가기 : https://kict119.broadwave.co.kr/notice/noticeview/" + notice.getId());
            }
        }

        //파일저장
        Iterator<String> files = multi.getFileNames();
        while(files.hasNext()) {
            String uploadFile = files.next();

            MultipartFile mFile = multi.getFile(uploadFile);
            //String fileName = mFile.getOriginalFilename();
            //파일이 존재할때만
            if (!mFile.isEmpty()) {
                //System.out.println("파일명 확인  : " + fileName);
                imageService.store(mFile,noticeSave);
            }
        }


//        log.info("공지사항 저장 성공 : " + noticeSave.toString() );
        return ResponseEntity.ok(res.success());


    }


    //공지사항 조회
    @PostMapping("list")
    public ResponseEntity noticeList(@RequestParam(value="subject", defaultValue="") String subject,
                                      @RequestParam(value="username", defaultValue="") String username,
                                      Pageable pageable){

        log.info("공지사항 조회 / 조회조건 : subject / '" + subject + "' username / '" + username + "'");


        Page<NoticeDto> notices = noticeService.findAllBySearchStrings(subject, username, pageable);
        return CommonUtils.ResponseEntityPage(notices);
    }

    @PostMapping("Popup")
    public ResponseEntity<Map<String,Object>> Popup(){
        List<NoticeIdStateDto> noticeIdStateDtos = noticeService.findByIdState();
//        log.info("noticeIdStateDtos : "+noticeIdStateDtos);

        List<Long> noticeId = new ArrayList<>();

        for(int i=0; i<noticeIdStateDtos.size(); i++){
            if(noticeIdStateDtos.get(i).getBnState()==1) {
                noticeId.add(noticeIdStateDtos.get(i).getId());
            }
        }
//        log.info("noticeId : "+noticeId);

        data.put("noticeId",noticeId);
        res.addResponse("data",data);
        return ResponseEntity.ok(res.success());

    }

    //공지사항내파일삭제
    @PostMapping("filedel")
    public ResponseEntity<Map<String,Object>> filedelete(@RequestParam(value="fileid", defaultValue="") Long fileid){

        log.info("공지사항 첨부파일삭제 시작/ 파일ID : '" + fileid + "'");

        int resultValue = imageService.uploadFileDelete(fileid);
        if (resultValue == -1){
            log.info("첨부파일 삭제시 에러발생('E015) fileID: '" + fileid + "'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E015.getCode(), ResponseErrorCode.E015.getDesc()));

        }
        log.info("공지사항 첨부파일삭제 성공/ 파일ID : '" + fileid + "'");
        return ResponseEntity.ok(res.success());
    }

    @PostMapping("del")
    public ResponseEntity<Map<String,Object>> noticeDelete(@RequestParam(value="noticeid", defaultValue="") Long noticeid){

        log.info("공지사항 삭제 시작 / 게시번호 ID : '" + noticeid + "'");

        Optional<Notice> optionalNotice = noticeService.findByIdNotice(noticeid);

        if (!optionalNotice.isPresent()){
            log.info("공지사항삭제 에러 데이터존재하지않습니다.('E003') fileID: '" + noticeid + "'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E003.getCode(), ResponseErrorCode.E003.getDesc()));
        }
        //첨부파일삭제
        List<UploadFile> uploadFiles = imageService.uploadFileList(noticeid);
        uploadFiles.forEach(uploadFile -> {
            imageService.uploadFileDelete(uploadFile.getId());
        });

        noticeService.delete(optionalNotice.get());
        log.info("공지사항 삭제 성공 / 게시번호 ID : '" + noticeid + "'");
        return ResponseEntity.ok(res.success());
    }

}
