package kr.co.broadwave.desk.notice;

import kr.co.broadwave.desk.accounts.Account;
import kr.co.broadwave.desk.accounts.AccountService;
import kr.co.broadwave.desk.common.AjaxResponse;
import kr.co.broadwave.desk.common.CommonUtils;
import kr.co.broadwave.desk.common.ResponseErrorCode;
import kr.co.broadwave.desk.notice.file.UploadFile;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;

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


    @Autowired
    public NoticeRestController(ModelMapper modelMapper, NoticeService noticeService, AccountService accountService, ImageService imageService) {
        this.modelMapper = modelMapper;
        this.noticeService = noticeService;
        this.accountService = accountService;
        this.imageService = imageService;
    }

    @PostMapping("image")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            UploadFile uploadedFile = imageService.store(file,null);
            return ResponseEntity.ok().body("/noticeimage/" + uploadedFile.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }



    @PostMapping("reg")
//    public ResponseEntity noticeSave(@ModelAttribute NoticeMapperDto noticeMapperDto , HttpServletRequest request){
    public ResponseEntity noticeSave(MultipartHttpServletRequest multi) throws Exception {
        //Notice notice = modelMapper.map(noticeMapperDto, Notice.class);


        String subject = multi.getParameter("subject");
        String content = multi.getParameter("content");
        Notice notice = Notice.builder()
                .subject(subject)
                .content(content)
                .build();


        String currentuserid = CommonUtils.getCurrentuser(multi);
        Optional<Account> optionalAccount = accountService.findByUserid(currentuserid);

        if (!optionalAccount.isPresent()) {
            log.info("공지사항 저장한 사람 아이디 미존재('E014) : '" + currentuserid + "'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E014.getCode(), ResponseErrorCode.E014.getDesc()));
        }
        notice.setInsert_id(currentuserid);
        notice.setInsert_name(optionalAccount.get().getUsername());
        notice.setInsertDateTime(LocalDateTime.now());
        notice.setModify_id(currentuserid);
        notice.setModify_name(optionalAccount.get().getUsername());
        notice.setModifyDateTime(LocalDateTime.now());
        notice.setHitCount(0);

        Notice noticeSave = noticeService.save(notice);

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


        //log.info("공지사항 저장 성공 : " + noticeSave.toString() );
        return ResponseEntity.ok(res.success());


    }
}
