package kr.co.broadwave.desk.record.file.mobilefile;

import kr.co.broadwave.desk.accounts.Account;
import kr.co.broadwave.desk.accounts.AccountService;
import kr.co.broadwave.desk.common.AjaxResponse;
import kr.co.broadwave.desk.common.CommonUtils;
import kr.co.broadwave.desk.common.ResponseErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author MinKyu
 * Date : 2020-08-05
 * Remark :
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

    // 사진찍은 파일 업로드
    @PostMapping("upload")
    public ResponseEntity<Map<String,Object>> upload(@RequestParam(value="comment", defaultValue="") String comment,
                                                     MultipartHttpServletRequest multi,
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
                mobileImageService.mobileUploadFile(mFile,optionalAccount.get(),comment);
            }
        }

        return ResponseEntity.ok(res.success());
    }

    // 모바일파일리스트(갤러리기능)
    @PostMapping("uploadList")
    public ResponseEntity<Map<String,Object>> uploadList(HttpServletRequest request){
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String currentuserid = CommonUtils.getCurrentuser(request);
        Optional<Account> optionalAccount = accountService.findByUserid(currentuserid);

        List<String> insertDate = new ArrayList<>();

        if(optionalAccount.isPresent()) {
            String role = optionalAccount.get().getRole().getCode();
            List<MobileUploadFileDto> mobileUploadFileDtoList;
            if(role.equals("ROLE_ADMIN")){
                mobileUploadFileDtoList =  mobileImageService.findByMobileUploadFileList(null);
            }else{
                mobileUploadFileDtoList =  mobileImageService.findByMobileUploadFileList(optionalAccount.get());
            }
//            log.info("mobileUploadFileDtoList : "+mobileUploadFileDtoList);
            for (int i = 0; i < mobileUploadFileDtoList.size(); i++) {
                if (!insertDate.contains(mobileUploadFileDtoList.get(i).getInsertDate())) {
                    insertDate.add(mobileUploadFileDtoList.get(i).getInsertDate());
                }
            }
//            log.info("insertDate : "+insertDate);

            data.put("mobileUploadFileDtoList",mobileUploadFileDtoList);
            data.put("insertDate",insertDate);
        }
        res.addResponse("data",data);
        return ResponseEntity.ok(res.success());
    }

    @PostMapping("uploadPage")
    public ResponseEntity<Map<String,Object>> uploadPage(HttpServletRequest request,
                                                         @RequestParam(value="s_from", defaultValue="") String s_from,
                                                         @RequestParam(value="s_to", defaultValue="") String s_to,
                                                         @RequestParam(value="s_username", defaultValue="") String s_username,
                                                         Pageable pageable){
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        LocalDateTime fromVal = null;
        LocalDateTime toVal= null;
        if(!s_from.equals("")) {
            fromVal = LocalDateTime.parse(s_from + "T00:00:00");
//            log.info("fromVal : "+fromVal);
        }
        if(!s_to.equals("")) {
            toVal = LocalDateTime.parse(s_to + "T23:59:59");
//            log.info("toVal : "+toVal);
        }

        String currentuserid = CommonUtils.getCurrentuser(request);
        Optional<Account> optionalAccount = accountService.findByUserid(currentuserid);

        if(optionalAccount.isPresent()) {
            String role = optionalAccount.get().getRole().getCode();
            Page<MobileUploadFileDto> mobileUploadFileDtoPage;
            if(role.equals("ROLE_ADMIN")){
                mobileUploadFileDtoPage =  mobileImageService.findByMobileUploadFilePage(null,fromVal,toVal,s_username,pageable);
            }else{
                mobileUploadFileDtoPage =  mobileImageService.findByMobileUploadFilePage(optionalAccount.get(),fromVal,toVal,s_username,pageable);
            }
//            log.info("mobileUploadFileDtoPage : "+mobileUploadFileDtoPage.getContent());
            if (mobileUploadFileDtoPage.getTotalElements() > 0) {
                data.put("datalist", mobileUploadFileDtoPage.getContent());
                data.put("total_page", mobileUploadFileDtoPage.getTotalPages());
                data.put("current_page", mobileUploadFileDtoPage.getNumber() + 1);
                data.put("total_rows", mobileUploadFileDtoPage.getTotalElements());
                data.put("current_rows", mobileUploadFileDtoPage.getNumberOfElements());
                res.addResponse("data", data);
            } else {
                data.put("total_page", mobileUploadFileDtoPage.getTotalPages());
                data.put("current_page", mobileUploadFileDtoPage.getNumber() + 1);
                data.put("total_rows", mobileUploadFileDtoPage.getTotalElements());
                data.put("current_rows", mobileUploadFileDtoPage.getNumberOfElements());
                res.addResponse("data", data);
            }
        }
        res.addResponse("data",data);
        return ResponseEntity.ok(res.success());
    }

    // 모바일파일삭제
    @PostMapping("mobileFileDel")
    public ResponseEntity<Map<String,Object>> mobileFileDel(@RequestParam(value="mobileid", defaultValue="") Long mobileid) {

        AjaxResponse res = new AjaxResponse();

        Optional<MobileUploadFile> optional = mobileImageService.findById(mobileid);

        if (!optional.isPresent()) {
            log.info("파일 삭제 에러 데이터가 존재하지않습니다.'" + mobileid + "'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E003.getCode(), ResponseErrorCode.E003.getDesc()));
        }
        String filePath = optional.get().getAfmFilePath();
        String fileThmPath = optional.get().getAfmFileThumPath();
        //기존파일삭제
        File file = new File(filePath);
        //썸네일파일삭제
        File file2 = new File(fileThmPath);
        file.delete();
        file2.delete();
        mobileImageService.delete(optional.get());

        return ResponseEntity.ok(res.success());
    }

}
